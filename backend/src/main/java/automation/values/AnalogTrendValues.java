package automation.values;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Component
public class AnalogTrendValues {

	private int TrendWriteToStringCells[] = { 131, 134 };
	private String TrendWriteStringValuesToCell[] = { /* 131 */"WAHR", /* 134 */"Aktueller Wert" };
	private int TrendWriteToDoubleCells[] = { 139, 141 };
	private double TrendWriteDoubleValuesToCell[] = { /* 139 */0, /* 141 */144 };

}
