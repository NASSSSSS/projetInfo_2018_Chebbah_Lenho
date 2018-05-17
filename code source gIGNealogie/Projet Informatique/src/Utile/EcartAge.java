package Utile;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 
 * @author lenhof-chebbah
 *
 */
public class EcartAge {

	/**
	 *Methode qui renvoit la valeur en millisecondes de l'ecart entre 2 dates
	 * @param date1 
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static long ecart(java.util.Date date1, java.util.Date date2) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		final java.util.Date ref = simpleDateFormat.parse("01/01/1970");
		long ecart = 0;
		
		if(date1.before(ref)){
			if(date2.before(ref)){
				if(date1.before(date2)){
				ecart = Math.abs(date1.getTime())- Math.abs(date2.getTime());
				}
				else{
					ecart = Math.abs(date2.getTime())- Math.abs(date1.getTime());
				}
			}
			else{
				ecart = Math.abs(date1.getTime())+ Math.abs(date2.getTime());
			}
		}
		else{
			if(date2.before(ref)){
			ecart = Math.abs(date1.getTime())+ Math.abs(date2.getTime());
			}
			else{
				if(date1.before(date2)){
					ecart = Math.abs(date2.getTime())- Math.abs(date1.getTime());
					}
					else{
						ecart = Math.abs(date1.getTime())- Math.abs(date2.getTime());
					}
			}
		}
		return ecart;
	}
}
