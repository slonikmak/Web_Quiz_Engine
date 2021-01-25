package engine.model;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return "";
        }
        return String.join(",", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        List<String> resultingSet = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(dbData, ",");
        while (st.hasMoreTokens())
            resultingSet.add(st.nextToken());
        return resultingSet;
    }
}
