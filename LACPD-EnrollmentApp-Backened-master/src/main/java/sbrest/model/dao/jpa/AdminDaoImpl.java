package sbrest.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sbrest.model.Admin;
import sbrest.model.dao.AdminDao;

@Repository
public class AdminDaoImpl implements AdminDao{
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	@Transactional
	public Admin getAdmin(String email) {
		return (Admin) entityManager.createQuery("SELECT c FROM Admin c WHERE c.email LIKE :em")
				.setParameter("em", email)
				.getResultList().get(0);
	}

	@Override
    public List<Admin> getAdmin() {
		return entityManager.createQuery("from Admin", Admin.class)
                .getResultList();
    }

	//TO SAVE CHANGES TO PASSWORD
	@Override
    @Transactional
    public Admin saveAdmin(Admin admin) {
    	return entityManager.merge(admin);
    }

//WHICH getAdmin() METHOD TO USE?
//	@Override
//	public Admin getAdmin(String password) {
//		 return entityManager.find(Admin.class, password);
//	}
}
