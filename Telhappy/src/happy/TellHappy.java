package happy;

import gui.InicialForm;

public class TellHappy {

	public static void main(String[] args) {

		DataAccess.loadDataBase();		
		InicialForm form = new InicialForm();
		form.setVisible(true);
	}

}
