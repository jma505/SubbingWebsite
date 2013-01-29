package org.jmanderson;

import org.hibernate.Session;
import org.jmanderson.subbing.hibernate.Organists;
import org.jmanderson.subbing.hibernate.SessionFactory;

public class TryIt {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Session session = SessionFactory.currentSession();
		Organists organist = (Organists) session.get(Organists.class, "jma");
//		SessionFactory.closeSession();
//		String xml = DataPreparer.getMyPiecesAsXml("jma");
		System.out.println(organist.getXml());

//		PasswordForm form = new PasswordForm();
//		form.setNewPassword1("fafner");
//		DataUpdater.updatePassword("jma", form);
		
	}

}
