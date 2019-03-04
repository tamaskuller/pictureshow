/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;

import controller.DB.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.PictureButtonTable;
import model.PictureComponentTable;
import model.PicturePaneTable;

/**
 *
 * @author Tamas Kuller
 */
public class PictureComponentTableJpaController implements Serializable {

    public PictureComponentTableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PictureComponentTable pictureComponentTable) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PictureButtonTable buttonId = pictureComponentTable.getButtonId();
            if (buttonId != null) {
                buttonId = em.getReference(buttonId.getClass(), buttonId.getButtonID());
                pictureComponentTable.setButtonId(buttonId);
            }
            PicturePaneTable parentpaneID = pictureComponentTable.getParentpaneID();
            if (parentpaneID != null) {
                parentpaneID = em.getReference(parentpaneID.getClass(), parentpaneID.getPaneID());
                pictureComponentTable.setParentpaneID(parentpaneID);
            }
            em.persist(pictureComponentTable);
            if (buttonId != null) {
                buttonId.getPictureComponentTableCollection().add(pictureComponentTable);
                buttonId = em.merge(buttonId);
            }
            if (parentpaneID != null) {
                parentpaneID.getPictureComponentTableCollection().add(pictureComponentTable);
                parentpaneID = em.merge(parentpaneID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PictureComponentTable pictureComponentTable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PictureComponentTable persistentPictureComponentTable = em.find(PictureComponentTable.class, pictureComponentTable.getComponentID());
            PictureButtonTable buttonIdOld = persistentPictureComponentTable.getButtonId();
            PictureButtonTable buttonIdNew = pictureComponentTable.getButtonId();
            PicturePaneTable parentpaneIDOld = persistentPictureComponentTable.getParentpaneID();
            PicturePaneTable parentpaneIDNew = pictureComponentTable.getParentpaneID();
            if (buttonIdNew != null) {
                buttonIdNew = em.getReference(buttonIdNew.getClass(), buttonIdNew.getButtonID());
                pictureComponentTable.setButtonId(buttonIdNew);
            }
            if (parentpaneIDNew != null) {
                parentpaneIDNew = em.getReference(parentpaneIDNew.getClass(), parentpaneIDNew.getPaneID());
                pictureComponentTable.setParentpaneID(parentpaneIDNew);
            }
            pictureComponentTable = em.merge(pictureComponentTable);
            if (buttonIdOld != null && !buttonIdOld.equals(buttonIdNew)) {
                buttonIdOld.getPictureComponentTableCollection().remove(pictureComponentTable);
                buttonIdOld = em.merge(buttonIdOld);
            }
            if (buttonIdNew != null && !buttonIdNew.equals(buttonIdOld)) {
                buttonIdNew.getPictureComponentTableCollection().add(pictureComponentTable);
                buttonIdNew = em.merge(buttonIdNew);
            }
            if (parentpaneIDOld != null && !parentpaneIDOld.equals(parentpaneIDNew)) {
                parentpaneIDOld.getPictureComponentTableCollection().remove(pictureComponentTable);
                parentpaneIDOld = em.merge(parentpaneIDOld);
            }
            if (parentpaneIDNew != null && !parentpaneIDNew.equals(parentpaneIDOld)) {
                parentpaneIDNew.getPictureComponentTableCollection().add(pictureComponentTable);
                parentpaneIDNew = em.merge(parentpaneIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pictureComponentTable.getComponentID();
                if (findPictureComponentTable(id) == null) {
                    throw new NonexistentEntityException("The pictureComponentTable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PictureComponentTable pictureComponentTable;
            try {
                pictureComponentTable = em.getReference(PictureComponentTable.class, id);
                pictureComponentTable.getComponentID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pictureComponentTable with id " + id + " no longer exists.", enfe);
            }
            PictureButtonTable buttonId = pictureComponentTable.getButtonId();
            if (buttonId != null) {
                buttonId.getPictureComponentTableCollection().remove(pictureComponentTable);
                buttonId = em.merge(buttonId);
            }
            PicturePaneTable parentpaneID = pictureComponentTable.getParentpaneID();
            if (parentpaneID != null) {
                parentpaneID.getPictureComponentTableCollection().remove(pictureComponentTable);
                parentpaneID = em.merge(parentpaneID);
            }
            em.remove(pictureComponentTable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PictureComponentTable> findPictureComponentTableEntities() {
        return findPictureComponentTableEntities(true, -1, -1);
    }

    public List<PictureComponentTable> findPictureComponentTableEntities(int maxResults, int firstResult) {
        return findPictureComponentTableEntities(false, maxResults, firstResult);
    }

    private List<PictureComponentTable> findPictureComponentTableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PictureComponentTable.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PictureComponentTable findPictureComponentTable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PictureComponentTable.class, id);
        } finally {
            em.close();
        }
    }

    public int getPictureComponentTableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PictureComponentTable> rt = cq.from(PictureComponentTable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
