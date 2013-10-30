package daiwei.mobile.db;

import java.util.List;

import android.test.AndroidTestCase;

import daiwei.mobile.db.LoginMess;
import daiwei.mobile.db.LoginMessDao;

public class TestLoginMessDao extends AndroidTestCase {

//	LoginMessDao dao = new LoginMessDao(getContext());
	
	
	public void testadd() throws Exception{
		LoginMessDao dao = new LoginMessDao(getContext());
		dao.add("01", "PSSSS","12131313-3-3-33","2013.03.08");
	}
	public void testfind() throws Exception{
		LoginMessDao dao = new LoginMessDao(getContext());
		boolean result = dao.find("PSSSS");
		assertEquals(true, result);
	}
	public void testfindall() throws Exception{
		LoginMessDao dao = new LoginMessDao(getContext());
		List<LoginMess> persons = dao.findAll();
	}
/*	public void testupdate() throws Exception{
		TestLoginMessDao dao = new TestLoginMessDao(getContext());
		dao.update("zhangsan", "123455678", "119");
	}
	public void testdelete() throws Exception{
		TestLoginMessDao dao = new TestLoginMessDao(getContext());
		dao.delete("zhangsan");
	}*/
	
}
