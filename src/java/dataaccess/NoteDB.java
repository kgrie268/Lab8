package dataaccess;

import domainmodel.Notes;
import domainmodel.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class NoteDB {

    public int insert(Notes notes) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(notes);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot insert " + notes.toString(), ex);
            throw new NotesDBException("Error inserting user");
        } finally {
            em.close();
        }
    }

    public int update(Notes notes) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(notes);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot update " + notes.toString(), ex);
            throw new NotesDBException("Error updating user");
        } finally {
            em.close();
        }
    }

    public List<Notes> getAll() throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Notes> notes = em.createNamedQuery("User.findAll", Notes.class).getResultList();
            return notes;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot read users", ex);
            throw new NotesDBException("Error getting Users");
        } finally {
            em.close();
        }
    }

    /**
     * Get a single user by their username.
     *
     * @param username The unique username.
     * @return A User object if found, null otherwise.
     * @throws NotesDBException
     */
    public Notes getNote(int noteId) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Notes notes = em.find(Notes.class, noteId);
            return notes;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot read users", ex);
            throw new NotesDBException("Error getting Users");
        } finally {
            em.close();
        }
    }

    public int delete(Notes notes) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.remove(em.merge(notes));
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot delete " + notes.toString(), ex);
            throw new NotesDBException("Error deleting user");
        } finally {
            em.close();
        }
    }
}
