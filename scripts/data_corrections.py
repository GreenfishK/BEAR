from datetime import timedelta, datetime
from pathlib import Path
from rdflib import Graph
import shutil


def correct(dataset: str, file: str, init_ts: datetime):
    """
    Bug 1: If either the alldata.TB_star_flat.ttl or alldata.TB_star_hierarchical.ttl dataset was constructed
    from computed .nt change sets there might be occurrences of faulty escapes, such as \\b, \\f and \\r in
    the alldata.TB_star_flat.ttl file. Triples, where such escapes occur are replaced by the corresponding
    correct triples from the original change sets. This bug only occurs in version 93.
    """

    version_ts = init_ts + timedelta(seconds=92)
    sys_ts_formatted = datetime.strftime(version_ts, "%Y-%m-%dT%H:%M:%S.%f")[:-3]
    xsd_datetime = "<http://www.w3.org/2001/XMLSchema#dateTime>"
    tz_offset = "+02:00"
    rdf_version_ts_res = '"{ts}{tz_offset}"^^{datetimeref}'.format(ts=sys_ts_formatted, tz_offset=tz_offset,
                                                                   datetimeref=xsd_datetime)
    if dataset == "rdf_star_flat":

        new_line1 = r'<<<http://dbpedia.org/resource/Rodeo_(Travis_Scott_album)> <http://dbpedia.org/property/cover> ' \
                    r'"{\\rtf1\\ansi\\ansicpg1252{\\fonttbl}\n{\\colortbl;\\red255\\green255\\blue255;"@en >> ' \
                    r'<https://github.com/GreenfishK/DataCitation/versioning/valid_from> ' \
                    + rdf_version_ts_res + r' . '
        new_line2 = r'<<<http://dbpedia.org/resource/Rodeo_(Travis_Scott_album)> <http://dbpedia.org/property/cover> ' \
                    r'"{\\rtf1\\ansi\\ansicpg1252{\\fonttbl}\n{\\colortbl;\\red255\\green255\\blue255;"@en >> ' \
                    r'<https://github.com/GreenfishK/DataCitation/versioning/valid_until> ' \
                    r'"9999-12-31T00:00:00.000+02:00"^^<http://www.w3.org/2001/XMLSchema#dateTime> . '

        rdf_star_flat_in = file
        with open(rdf_star_flat_in) as fin, open("tmp_out.ttl", "w") as fout:
            for line in fin:
                if r"\\\rtf1\\ansi\\ansicpg1252{\\" in line:
                    line = new_line1 + "\n" + new_line2
                    print(line)
                if r"{\\colortbl;\\\red255\\green255" in line:
                    line = ""
                    print(line)
                fout.write(line)
        fin.close()
        fout.close()
        shutil.move("tmp_out.ttl", file)

    if dataset == "rdf_star_hierarchical":
        new_line1 = r'<<<<<http://dbpedia.org/resource/Rodeo_(Travis_Scott_album)> <http://dbpedia.org/property/cover> ' \
                    r'"{\\rtf1\\ansi\\ansicpg1252{\\fonttbl}\n{\\colortbl;\\red255\\green255\\blue255;"@en >> ' \
                    r'<https://github.com/GreenfishK/DataCitation/versioning/valid_from> ' \
                    + rdf_version_ts_res + r'>> ' \
                    r'<https://github.com/GreenfishK/DataCitation/versioning/valid_until> ' \
                    r'"9999-12-31T00:00:00.000+02:00"^^<http://www.w3.org/2001/XMLSchema#dateTime> .'

        rdf_star_hierarchical_in = file
        with open(rdf_star_hierarchical_in) as fin, open("tmp_out.ttl", "w") as fout:
            for line in fin:
                if r"\\\rtf1\\ansi\\ansicpg1252{\\" in line:
                    line = new_line1 + "\n"
                    print(line)
                if r"{\\colortbl;\\\red255\\green255" in line:
                    line = ""
                    print(line)
                fout.write(line)
        fin.close()
        fout.close()
        shutil.move("tmp_out.ttl", file)
