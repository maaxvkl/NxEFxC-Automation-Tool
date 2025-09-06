package automation.values;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Component
public class AnalogInputValues {

	private int AIwriteToStringCells[] = { 26, 37, 38, 40, 42, 48, 49, 50, 51, 52, 56, 57, 63, 86, 93, 94, 106, 107,
			108, 109, 111, 112, 113, 114, 118, 119, 125 };
	private String AIwriteStringValuesToCell[] = { /* 26 */"10ths", /* 37 */"Custom", /* 38 */"Other", /* 40 */"FALSCH",
			/* 42 */"FALSCH", /* 48 */"FALSCH", /* 49 */"FALSCH", /* 50 */"WAHR", /* 51 */"FALSCH", /* 52 */"WAHR",
			/* 56 */"WAHR", /* 57 */"WAHR", /* 63 */"Alarm", /* 86 */"WAHR", /* 93 */"Allgemein", /* 94 */"WAHR",
			/* 106 */"WAHR", /* 107 */"FALSCH", /* 108 */"FALSCH", /* 109 */"FALSCH", /* 111 */"FALSCH",
			/* 112 */"WAHR", /* 113 */"FALSCH", /* 114 */"WAHR", /* 118 */"WAHR", /* 119 */"WAHR", /* 125 */"Alarm" };
	private int AIwriteToDoubleCells[] = { 27, 30, 39, 41, 43, 44, 58, 59, 60, 61, 64, 70, 71, 73, 99, 100, 115, 120,
			121, 122, 126 };
	private double AIwriteDoubleValuesToCell[] = { /* 27 */0.2, /* 30 */0, /* 39 */100, /* 41 */0.02, /* 43 */0.1,
			/* 44 */720, /* 58 */0, /* 59 */100, /* 60 */0, /* 61 */0, /* 64 */0, /* 70 */0, /* 71 */0, /* 73 */0,
			/* 99 */1, /* 100 */0.01, /* 115 */1, /* 119 */0, /* 121 */100, /* 122 */0, /* 126 */0 };

}
