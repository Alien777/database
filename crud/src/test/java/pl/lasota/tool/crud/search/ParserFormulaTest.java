package pl.lasota.tool.crud.search;

import org.junit.Test;
import pl.lasota.tool.crud.serach.Field;
import pl.lasota.tool.crud.serach.FieldNumber;
import pl.lasota.tool.crud.serach.FieldString;
import pl.lasota.tool.crud.serach.ParserFormula;

import java.text.ParseException;
import java.util.List;

public class ParserFormulaTest {

    @Test
    public void testParser() throws ParseException {
        ParserFormula parserFormula = new ParserFormula();
        List<Field> parser = parserFormula.parser("cityon.pl/get?username=test;or&login=adas");
        parser.forEach(field -> {
            if(field instanceof FieldNumber)
            {
                System.out.println(field);
            }else
            {
                System.out.println(field);
            }

        });
        assert parser.size() == 2;
    }
}
