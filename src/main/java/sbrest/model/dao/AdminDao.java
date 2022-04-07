package sbrest.model.dao;

import java.util.List;

import sbrest.model.Admin;

public interface AdminDao {
	Admin getAdmin(String email);

    List<Admin> getAdmin();
	
    void createAdmin(String email, String password, String firstName, String middleName, String lastName);
    
    Admin saveAdmin(Admin admin);
    
//	Admin getAdmin(String password);
}
