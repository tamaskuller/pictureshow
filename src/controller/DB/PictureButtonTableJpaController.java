/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;

import controller.DB.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.PicturePaneTable;
import model.PictureComponentTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.PictureButtonTable;

/**
 *
 * @author Tamas Kuller
 */
public class PictureButtonTableJpaController implements Serializable {

    public PictureButtonTableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PictureButtonTable pictureButtonTable) {
        if (pictureButtonTable.getPictureComponentTableCollection() == null) {
            pictureButtonTable.setPictureComponentTableCollection(new ArrayList<PictureComponentTable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PicturePaneTable parentpaneID = pictureButtonTable.getParentpaneID();
            if (parentpaneID != null) {
                parentpaneID = em.getReference(parentpaneID.getClass(), parentpaneID.getPaneID());
                pictureButtonTable.setParentpaneID(parentpaneID);
            }
            Collection<PictureComponentTable> attachedPictureComponentTableCollection = new ArrayList<PictureComponentTable>();
            for (PictureComponentTable pictureComponentTableCollectionPictureComponentTableToAttach : pictureButtonTable.getPictureComponentTableCollection()) {
                pictureComponentTableCollectionPictureComponentTableToAttach = em.getReference(pictureComponentTableCollectionPictureComponentTableToAttach.getClass(), pictureComponentTableCollectionPictureComponentTableToAttach.getComponentID());
                attachedPictureComponentTableCollection.add(pictureComponentTableCollectionPictureComponentTableToAttach);
            }
            pictureButtonTable.setPictureComponentTableCollection(attachedPictureComponentTableCollection);
            em.persist(pictureButtonTable);
            if (parentpaneID != null) {
                parentpaneID.getPictureButtonTableCollection().add(pictureButtonTable);
                parentpaneID = em.merge(parentpaneID);
            }
            for (PictureComponentTable pictureComponentTableCollectionPictureComponentTable : pictureButtonTable.getPictureComponentTableCollection()) {
                PictureButtonTable oldButtonIdOfPictureComponentTableCollectionPictureComponentTable = pictureComponentTableCollectionPictureComponentTable.getButtonId();
                pictureComponentTableCollectionPictureComponentTable.setButtonId(pictureButtonTable);
                pictureComponentTableCollectionPictureComponentTable = em.merge(pictureComponentTableCollectionPictureComponentTable);
                if (oldButtonIdOfPictureComponentTableCollectionPictureComponentTable != null) {
                    oldButtonIdOfPictureComponentTableCollectionPictureComponentTable.getPictureComponentTableCollection().remove(pictureComponentTableCollectionPictureComponentTable);
                    oldButtonIdOfPictureComponentTableCollectionPictureComponentTable = em.merge(oldButtonIdOfPictureComponentTableCollectionPictureComponentTable);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PictureButtonTable pictureButtonTable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PictureButtonTable persistentPictureButtonTable = em.find(PictureButtonTable.class, pictureButtonTable.getButtonID());
            PicturePaneTable parentpaneIDOld = persistentPictureButtonTable.getParentpaneID();
            PicturePaneTable parentpaneIDNew = pictureButtonTable.getParentpaneID();
            Collection<PictureComponentTable> pictureComponentTableCollectionOld = persistentPictureButtonTable.getPictureComponentTableCollection();
            Collection<PictureComponentTable> pictureComponentTableCollectionNew = pictureButtonTable.getPictureComponentTableCollection();
            if (parentpaneIDNew != null) {
                parentpaneIDNew = em.getReference(parentpaneIDNew.getClass(), parentpaneIDNew.getPaneID());
                pictureButtonTable.setParentpaneID(parentpaneIDNew);
            }
            Collection<PictureComponentTable> attachedPictureComponentTableCollectionNew = new ArrayList<PictureComponentTable>();
            for (PictureComponentTable pictureComponentTableCollectionNewPictureComponentTableToAttach : pictureComponentTableCollectionNew) {
                pictureComponentTableCollectionNewPictureComponentTableToAttach = em.getReference(pictureComponentTableCollectionNewPictureComponentTableToAttach.getClass(), pictureComponentTableCollectionNewPictureComponentTableToAttach.getComponentID());
                attachedPictureComponentTableCollectionNew.add(pictureComponentTableCollectionNewPictureComponentTableToAttach);
            }
            pictureComponentTableCollectionNew = attachedPictureComponentTableCollectionNew;
            pictureButtonTable.setPictureComponentTableCollection(pictureComponentTableCollectionNew);
            pictureButtonTable = em.merge(pictureButtonTable);
            if (parentpaneIDOld != null && !parentpaneIDOld.equals(parentpaneIDNew)) {
                parentpaneIDOld.getPictureButtonTableCollection().remove(pictureButtonTable);
                parentpaneIDOld = em.merge(parentpaneIDOld);
            }
            if (parentpaneIDNew != null && !parentpaneIDNew.equals(parentpaneIDOld)) {
                parentpaneIDNew.getPictureButtonTableCollection().add(pictureButtonTable);
                parentpaneIDNew = em.merge(parentpaneIDNew);
            }
            for (PictureComponentTable pictureComponentTableCollectionOldPictureComponentTable : pictureComponentTableCollectionOld) {
                if (!pictureComponentTableCollectionNew.contains(pictureComponentTableCollectionOldPictureComponentTable)) {
                    pictureComponentTableCollectionOldPictureComponentTable.setButtonId(null);
                    pictureComponentTableCollectionOldPictureComponentTable = em.merge(pictureComponentTableCollectionOldPictureComponentTable);
                }
            }
            for (PictureComponentTable pictureComponentTableCollectionNewPictureComponentTable : pictureComponentTableCollectionNew) {
                if (!pictureComponentTableCollectionOld.contains(pictureComponentTableCollectionNewPictureComponentTable)) {
                    PictureButtonTable oldButtonIdOfPictureComponentTableCollectionNewPictureComponentTable = pictureComponentTableCollectionNewPictureComponentTable.getButtonId();
                    pictureComponentTableCollectionNewPictureComponentTable.setButtonId(pictureButtonTable);
                    pictureComponentTableCollectionNewPictureComponentTable = em.merge(pictureComponentTableCollectionNewPictureComponentTable);
                    if (oldButtonIdOfPictureComponentTableCollectionNewPictureComponentTable != null && !oldButtonIdOfPictureComponentTableCollectionNewPictureComponentTable.equals(pictureButtonTable)) {
                        oldButtonIdOfPictureComponentTableCollectionNewPictureComponentTable.getPictureComponentTableCollection().remove(pictureComponentTableCollectionNewPictureComponentTable);
                        oldButtonIdOfPictureComponentTableCollectionNewPictureComponentTable = em.merge(oldButtonIdOfPictureComponentTableCollectionNewPictureComponentTable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pictureButtonTable.getButtonID();
                if (findPictureButtonTable(id) == null) {
                    throw new NonexistentEntityException("The pictureButtonTable with id " + id + " no longer exists.");
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
            PictureButtonTable pictureButtonTable;
            try {
                pictureButtonTable = em.getReference(PictureButtonTable.class, id);
                pictureButtonTable.getButtonID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pictureButtonTable with id " + id + " no longer exists.", enfe);
            }
            PicturePaneTable parentpaneID = pictureButtonTable.getParentpaneID();
            if (parentpaneID != null) {
                parentpaneID.getPictureButtonTableCollection().remove(pictureButtonTable);
                parentpaneID = em.merge(parentpaneID);
            }
            Collection<PictureComponentTable> pictureComponentTableCollection = pictureButtonTable.getPictureComponentTableCollection();
            for (PictureComponentTable pictureComponentTableCollectionPictureComponentTable : pictureComponentTableCollection) {
                pictureComponentTableCollectionPictureComponentTable.setButtonId(null);
                pictureComponentTableCollectionPictureComponentTable = em.merge(pictureComponentTableCollectionPictureComponentTable);
            }
            em.remove(pictureButtonTable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PictureButtonTable> findPictureButtonTableEntities() {
        return findPictureButtonTableEntities(true, -1, -1);
    }

    public List<PictureButtonTable> findPictureButtonTableEntities(int maxResults, int firstResult) {
        return findPictureButtonTableEntities(false, maxResults, firstResult);
    }

    private List<PictureButtonTable> findPictureButtonTableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PictureButtonTable.class));
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

    public PictureButtonTable findPictureButtonTable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PictureButtonTable.class, id);
        } finally {
            em.close();
        }
    }

    public int getPictureButtonTableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PictureButtonTable> rt = cq.from(PictureButtonTable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
