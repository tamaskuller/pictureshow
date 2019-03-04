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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.PictureFrameTable;

/**
 *
 * @author Tamas Kuller
 */
public class PictureFrameTableJpaController implements Serializable {

    public PictureFrameTableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PictureFrameTable pictureFrameTable) {
        if (pictureFrameTable.getPicturePaneTableCollection() == null) {
            pictureFrameTable.setPicturePaneTableCollection(new ArrayList<PicturePaneTable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PicturePaneTable> attachedPicturePaneTableCollection = new ArrayList<PicturePaneTable>();
            for (PicturePaneTable picturePaneTableCollectionPicturePaneTableToAttach : pictureFrameTable.getPicturePaneTableCollection()) {
                picturePaneTableCollectionPicturePaneTableToAttach = em.getReference(picturePaneTableCollectionPicturePaneTableToAttach.getClass(), picturePaneTableCollectionPicturePaneTableToAttach.getPaneID());
                attachedPicturePaneTableCollection.add(picturePaneTableCollectionPicturePaneTableToAttach);
            }
            pictureFrameTable.setPicturePaneTableCollection(attachedPicturePaneTableCollection);
            em.persist(pictureFrameTable);
            for (PicturePaneTable picturePaneTableCollectionPicturePaneTable : pictureFrameTable.getPicturePaneTableCollection()) {
                PictureFrameTable oldParentframeIDOfPicturePaneTableCollectionPicturePaneTable = picturePaneTableCollectionPicturePaneTable.getParentframeID();
                picturePaneTableCollectionPicturePaneTable.setParentframeID(pictureFrameTable);
                picturePaneTableCollectionPicturePaneTable = em.merge(picturePaneTableCollectionPicturePaneTable);
                if (oldParentframeIDOfPicturePaneTableCollectionPicturePaneTable != null) {
                    oldParentframeIDOfPicturePaneTableCollectionPicturePaneTable.getPicturePaneTableCollection().remove(picturePaneTableCollectionPicturePaneTable);
                    oldParentframeIDOfPicturePaneTableCollectionPicturePaneTable = em.merge(oldParentframeIDOfPicturePaneTableCollectionPicturePaneTable);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PictureFrameTable pictureFrameTable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PictureFrameTable persistentPictureFrameTable = em.find(PictureFrameTable.class, pictureFrameTable.getFrameID());
            Collection<PicturePaneTable> picturePaneTableCollectionOld = persistentPictureFrameTable.getPicturePaneTableCollection();
            Collection<PicturePaneTable> picturePaneTableCollectionNew = pictureFrameTable.getPicturePaneTableCollection();
            Collection<PicturePaneTable> attachedPicturePaneTableCollectionNew = new ArrayList<PicturePaneTable>();
            for (PicturePaneTable picturePaneTableCollectionNewPicturePaneTableToAttach : picturePaneTableCollectionNew) {
                picturePaneTableCollectionNewPicturePaneTableToAttach = em.getReference(picturePaneTableCollectionNewPicturePaneTableToAttach.getClass(), picturePaneTableCollectionNewPicturePaneTableToAttach.getPaneID());
                attachedPicturePaneTableCollectionNew.add(picturePaneTableCollectionNewPicturePaneTableToAttach);
            }
            picturePaneTableCollectionNew = attachedPicturePaneTableCollectionNew;
            pictureFrameTable.setPicturePaneTableCollection(picturePaneTableCollectionNew);
            pictureFrameTable = em.merge(pictureFrameTable);
            for (PicturePaneTable picturePaneTableCollectionOldPicturePaneTable : picturePaneTableCollectionOld) {
                if (!picturePaneTableCollectionNew.contains(picturePaneTableCollectionOldPicturePaneTable)) {
                    picturePaneTableCollectionOldPicturePaneTable.setParentframeID(null);
                    picturePaneTableCollectionOldPicturePaneTable = em.merge(picturePaneTableCollectionOldPicturePaneTable);
                }
            }
            for (PicturePaneTable picturePaneTableCollectionNewPicturePaneTable : picturePaneTableCollectionNew) {
                if (!picturePaneTableCollectionOld.contains(picturePaneTableCollectionNewPicturePaneTable)) {
                    PictureFrameTable oldParentframeIDOfPicturePaneTableCollectionNewPicturePaneTable = picturePaneTableCollectionNewPicturePaneTable.getParentframeID();
                    picturePaneTableCollectionNewPicturePaneTable.setParentframeID(pictureFrameTable);
                    picturePaneTableCollectionNewPicturePaneTable = em.merge(picturePaneTableCollectionNewPicturePaneTable);
                    if (oldParentframeIDOfPicturePaneTableCollectionNewPicturePaneTable != null && !oldParentframeIDOfPicturePaneTableCollectionNewPicturePaneTable.equals(pictureFrameTable)) {
                        oldParentframeIDOfPicturePaneTableCollectionNewPicturePaneTable.getPicturePaneTableCollection().remove(picturePaneTableCollectionNewPicturePaneTable);
                        oldParentframeIDOfPicturePaneTableCollectionNewPicturePaneTable = em.merge(oldParentframeIDOfPicturePaneTableCollectionNewPicturePaneTable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pictureFrameTable.getFrameID();
                if (findPictureFrameTable(id) == null) {
                    throw new NonexistentEntityException("The pictureFrameTable with id " + id + " no longer exists.");
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
            PictureFrameTable pictureFrameTable;
            try {
                pictureFrameTable = em.getReference(PictureFrameTable.class, id);
                pictureFrameTable.getFrameID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pictureFrameTable with id " + id + " no longer exists.", enfe);
            }
            Collection<PicturePaneTable> picturePaneTableCollection = pictureFrameTable.getPicturePaneTableCollection();
            for (PicturePaneTable picturePaneTableCollectionPicturePaneTable : picturePaneTableCollection) {
                picturePaneTableCollectionPicturePaneTable.setParentframeID(null);
                picturePaneTableCollectionPicturePaneTable = em.merge(picturePaneTableCollectionPicturePaneTable);
            }
            em.remove(pictureFrameTable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PictureFrameTable> findPictureFrameTableEntities() {
        return findPictureFrameTableEntities(true, -1, -1);
    }

    public List<PictureFrameTable> findPictureFrameTableEntities(int maxResults, int firstResult) {
        return findPictureFrameTableEntities(false, maxResults, firstResult);
    }

    private List<PictureFrameTable> findPictureFrameTableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PictureFrameTable.class));
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

    public PictureFrameTable findPictureFrameTable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PictureFrameTable.class, id);
        } finally {
            em.close();
        }
    }

    public int getPictureFrameTableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PictureFrameTable> rt = cq.from(PictureFrameTable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
