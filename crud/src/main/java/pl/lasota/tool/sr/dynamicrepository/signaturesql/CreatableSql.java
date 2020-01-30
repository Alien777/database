package pl.lasota.tool.sr.dynamicrepository.signaturesql;

public interface CreatableSql {
    String create();

    default String format(String raw) {
        return raw.trim()
                .replaceAll("\\s{2,}", " ")
                .replaceAll("\\(\\s{1,}", "(")
                .replaceAll("\\s{1,}\\)", ")")
                .replaceAll("\\s{1,};", ";")
                .replaceAll("\\s{1,},", ",")
                .replaceAll(",",",\n");
    }
}
