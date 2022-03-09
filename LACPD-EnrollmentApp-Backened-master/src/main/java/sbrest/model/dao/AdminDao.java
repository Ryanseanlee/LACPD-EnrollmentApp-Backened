package sbrest.model.dao;

import java.util.List;

import sbrest.model.Admin;

public interface AdminDao {
	Admin getAdmin(String email);

    List<Admin> getAdmin();
	
    Admin saveAdmin(Admin admin);
    
//	Admin getAdmin(String password);
}
