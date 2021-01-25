package engine.model;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class IntListConverter implements AttributeConverter<List<Integer>, String> {
    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        if (attribute == null) {
            return "";
        }
        return attribute.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        List<Integer> resultingSet = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(dbData, ",");
        while (st.hasMoreTokens())
            resultingSet.add(Integer.parseInt(st.nextToken()));
        return resultingSet;
    }
}
