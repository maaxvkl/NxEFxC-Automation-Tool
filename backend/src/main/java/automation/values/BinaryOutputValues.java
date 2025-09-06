package automation.values;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor
public class BinaryOutputValues {
	
    
private int BOwriteToStringCells [] = {25,35,36,47,48,49,50,51,52,63,86,93,94,97,105,106,107,108,109,111,112,113,114,125};
private String BOwriteStringValuestoCell [] = {/*25*/"Normal",/*35*/"WAHR",/*36*/"FALSCH",/*47*/"ALLGEMEIN",/*48*/"FALSCH",/*49*/"FALSCH",/*50*/"WAHR",/*51*/"FALSCH",/*52*/"WAHR",/*63*/"Ereignis",/*86*/"WAHR",/*93*/"Allgemein",/*94*/"WAHR",
                                       /*97*/"Inaktiv Aktiv",/*105*/"Inaktiv",/*106*/"WAHR",
                                      /*107*/"FALSCH",/*108*/"FALSCH",/*109*/"FALSCH",/*111*/"FALSCH",/*112*/"WAHR",/*113*/"FALSCH",/*114*/"WAHR",/*125*/"Alarm", };
private int BOwriteToDoubleCells [] = {61,64,103,104,115};
private double BOwriteDoubleValuesToCell [] = {/*61*/0,/*64*/0,/*103*/0,/*104*/0,/*115*/1,};

}
