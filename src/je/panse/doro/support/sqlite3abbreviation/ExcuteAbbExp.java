package je.panse.doro.support.sqlite3abbreviation;

import java.sql.SQLException;

public class ExcuteAbbExp {

	public static void main(String[] args) {
		try {
			AbbreviationExpander.main("   #  Thyroid Disease\n"
					+ "   #  Asthma\n"
//					+ "   #  :c  without meds\n"
					+ "   #  :cd admission\n"
					+ "   #  :t   ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
