/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;

import controller.DB.exceptions.IllegalOrphanException;
import controller.DB.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.PictureFrameTable;
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
public class PicturePaneTableJpaController implements Serializable {

    public PicturePaneTableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PicturePaneTable picturePaneTable) {
        if (picturePaneTable.getPictureComponentTableCollection() == null) {
            picturePaneTable.setPictureComponentTableCollection(new ArrayList<PictureComponentTable>());
        }
        if (picturePaneTable.getPicturePaneTableCollection() == null) {
            picturePaneTable.setPicturePaneTableCollection(new ArrayList<PicturePaneTable>());
        }
        if (picturePaneTable.getPictureButtonTableCollection() == null) {
            picturePaneTable.setPictureButtonTableCollection(new ArrayList<PictureButtonTable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PictureFrameTable parentframeID = picturePaneTable.getParentframeID();
            if (parentframeID != null) {
                parentframeID = em.getReference(parentframeID.getClass(), parentframeID.getFrameID());
                picturePaneTable.setParentframeID(parentframeID);
            }
            PicturePaneTable parentpaneID = picturePaneTable.getParentpaneID();
            if (parentpaneID != null) {
                parentpaneID = em.getReference(parentpaneID.getClass(), parentpaneID.getPaneID());
                picturePaneTable.setParentpaneID(parentpaneID);
            }
            Collection<PictureComponentTable> attachedPictureComponentTableCollection = new ArrayList<PictureComponentTable>();
            for (PictureComponentTable pictureComponentTableCollectionPictureComponentTableToAttach : picturePaneTable.getPictureComponentTableCollection()) {
                pictureComponentTableCollectionPictureComponentTableToAttach = em.getReference(pictureComponentTableCollectionPictureComponentTableToAttach.getClass(), pictureComponentTableCollectionPictureComponentTableToAttach.getComponentID());
                attachedPictureComponentTableCollection.add(pictureComponentTableCollectionPictureComponentTableToAttach);
            }
            picturePaneTable.setPictureComponentTableCollection(attachedPictureComponentTableCollection);
            Collection<PicturePaneTable> attachedPicturePaneTableCollection = new ArrayList<PicturePaneTable>();
            for (PicturePaneTable picturePaneTableCollectionPicturePaneTableToAttach : picturePaneTable.getPicturePaneTableCollection()) {
                picturePaneTableCollectionPicturePaneTableToAttach = em.getReference(picturePaneTableCollectionPicturePaneTableToAttach.getClass(), picturePaneTableCollectionPicturePaneTableToAttach.getPaneID());
                attachedPicturePaneTableCollection.add(picturePaneTableCollectionPicturePaneTableToAttach);
            }
            picturePaneTable.setPicturePaneTableCollection(attachedPicturePaneTableCollection);
            Collection<PictureButtonTable> attachedPictureButtonTableCollection = new ArrayList<PictureButtonTable>();
            for (PictureButtonTable pictureButtonTableCollectionPictureButtonTableToAttach : picturePaneTable.getPictureButtonTableCollection()) {
                pictureButtonTableCollectionPictureButtonTableToAttach = em.getReference(pictureButtonTableCollectionPictureButtonTableToAttach.getClass(), pictureButtonTableCollectionPictureButtonTableToAttach.getButtonID());
                attachedPictureButtonTableCollection.add(pictureButtonTableCollectionPictureButtonTableToAttach);
            }
            picturePaneTable.setPictureButtonTableCollection(attachedPictureButtonTableCollection);
            em.persist(picturePaneTable);
            if (parentframeID != null) {
                parentframeID.getPicturePaneTableCollection().add(picturePaneTable);
                parentframeID = em.merge(parentframeID);
            }
            if (parentpaneID != null) {
                parentpaneID.getPicturePaneTableCollection().add(picturePaneTable);
                parentpaneID = em.merge(parentpaneID);
            }
            for (PictureComponentTable pictureComponentTableCollectionPictureComponentTable : picturePaneTable.getPictureComponentTableCollection()) {
                PicturePaneTable oldParentpaneIDOfPictureComponentTableCollectionPictureComponentTable = pictureComponentTableCollectionPictureComponentTable.getParentpaneID();
                pictureComponentTableCollectionPictureComponentTable.setParentpaneID(picturePaneTable);
                pictureComponentTableCollectionPictureComponentTable = em.merge(pictureComponentTableCollectionPictureComponentTable);
                if (oldParentpaneIDOfPictureComponentTableCollectionPictureComponentTable != null) {
                    oldParentpaneIDOfPictureComponentTableCollectionPictureComponentTable.getPictureComponentTableCollection().remove(pictureComponentTableCollectionPictureComponentTable);
                    oldParentpaneIDOfPictureComponentTableCollectionPictureComponentTable = em.merge(oldParentpaneIDOfPictureComponentTableCollectionPictureComponentTable);
                }
            }
            for (PicturePaneTable picturePaneTableCollectionPicturePaneTable : picturePaneTable.getPicturePaneTableCollection()) {
                PicturePaneTable oldParentpaneIDOfPicturePaneTableCollectionPicturePaneTable = picturePaneTableCollectionPicturePaneTable.getParentpaneID();
                picturePaneTableCollectionPicturePaneTable.setParentpaneID(picturePaneTable);
                picturePaneTableCollectionPicturePaneTable = em.merge(picturePaneTableCollectionPicturePaneTable);
                if (oldParentpaneIDOfPicturePaneTableCollectionPicturePaneTable != null) {
                    oldParentpaneIDOfPicturePaneTableCollectionPicturePaneTable.getPicturePaneTableCollection().remove(picturePaneTableCollectionPicturePaneTable);
                    oldParentpaneIDOfPicturePaneTableCollectionPicturePaneTable = em.merge(oldParentpaneIDOfPicturePaneTableCollectionPicturePaneTable);
                }
            }
            for (PictureButtonTable pictureButtonTableCollectionPictureButtonTable : picturePaneTable.getPictureButtonTableCollection()) {
                PicturePaneTable oldParentpaneIDOfPictureButtonTableCollectionPictureButtonTable = pictureButtonTableCollectionPictureButtonTable.getParentpaneID();
                pictureButtonTableCollectionPictureButtonTable.setParentpaneID(picturePaneTable);
                pictureButtonTableCollectionPictureButtonTable = em.merge(pictureButtonTableCollectionPictureButtonTable);
                if (oldParentpaneIDOfPictureButtonTableCollectionPictureButtonTable != null) {
                    oldParentpaneIDOfPictureButtonTableCollectionPictureButtonTable.getPictureButtonTableCollection().remove(pictureButtonTableCollectionPictureButtonTable);
                    oldParentpaneIDOfPictureButtonTableCollectionPictureButtonTable = em.merge(oldParentpaneIDOfPictureButtonTableCollectionPictureButtonTable);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PicturePaneTable picturePaneTable) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PicturePaneTable persistentPicturePaneTable = em.find(PicturePaneTable.class, picturePaneTable.getPaneID());
            PictureFrameTable parentframeIDOld = persistentPicturePaneTable.getParentframeID();
            PictureFrameTable parentframeIDNew = picturePaneTable.getParentframeID();
            PicturePaneTable parentpaneIDOld = persistentPicturePaneTable.getParentpaneID();
            PicturePaneTable parentpaneIDNew = picturePaneTable.getParentpaneID();
            Collection<PictureComponentTable> pictureComponentTableCollectionOld = persistentPicturePaneTable.getPictureComponentTableCollection();
            Collection<PictureComponentTable> pictureComponentTableCollectionNew = picturePaneTable.getPictureComponentTableCollection();
            Collection<PicturePaneTable> picturePaneTableCollectionOld = persistentPicturePaneTable.getPicturePaneTableCollection();
            Collection<PicturePaneTable> picturePaneTableCollectionNew = picturePaneTable.getPicturePaneTableCollection();
            Collection<PictureButtonTable> pictureButtonTableCollectionOld = persistentPicturePaneTable.getPictureButtonTableCollection();
            Collection<PictureButtonTable> pictureButtonTableCollectionNew = picturePaneTable.getPictureButtonTableCollection();
            List<String> illegalOrphanMessages = null;
            for (PictureComponentTable pictureComponentTableCollectionOldPictureComponentTable : pictureComponentTableCollectionOld) {
                if (!pictureComponentTableCollectionNew.contains(pictureComponentTableCollectionOldPictureComponentTable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PictureComponentTable " + pictureComponentTableCollectionOldPictureComponentTable + " since its parentpaneID field is not nullable.");
                }
            }
            for (PictureButtonTable pictureButtonTableCollectionOldPictureButtonTable : pictureButtonTableCollectionOld) {
                if (!pictureButtonTableCollectionNew.contains(pictureButtonTableCollectionOldPictureButtonTable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PictureButtonTable " + pictureButtonTableCollectionOldPictureButtonTable + " since its parentpaneID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (parentframeIDNew != null) {
                parentframeIDNew = em.getReference(parentframeIDNew.getClass(), parentframeIDNew.getFrameID());
                picturePaneTable.setParentframeID(parentframeIDNew);
            }
            if (parentpaneIDNew != null) {
                parentpaneIDNew = em.getReference(parentpaneIDNew.getClass(), parentpaneIDNew.getPaneID());
                picturePaneTable.setParentpaneID(parentpaneIDNew);
            }
            Collection<PictureComponentTable> attachedPictureComponentTableCollectionNew = new ArrayList<PictureComponentTable>();
            for (PictureComponentTable pictureComponentTableCollectionNewPictureComponentTableToAttach : pictureComponentTableCollectionNew) {
                pictureComponentTableCollectionNewPictureComponentTableToAttach = em.getReference(pictureComponentTableCollectionNewPictureComponentTableToAttach.getClass(), pictureComponentTableCollectionNewPictureComponentTableToAttach.getComponentID());
                attachedPictureComponentTableCollectionNew.add(pictureComponentTableCollectionNewPictureComponentTableToAttach);
            }
            pictureComponentTableCollectionNew = attachedPictureComponentTableCollectionNew;
            picturePaneTable.setPictureComponentTableCollection(pictureComponentTableCollectionNew);
            Collection<PicturePaneTable> attachedPicturePaneTableCollectionNew = new ArrayList<PicturePaneTable>();
            for (PicturePaneTable picturePaneTableCollectionNewPicturePaneTableToAttach : picturePaneTableCollectionNew) {
                picturePaneTableCollectionNewPicturePaneTableToAttach = em.getReference(picturePaneTableCollectionNewPicturePaneTableToAttach.getClass(), picturePaneTableCollectionNewPicturePaneTableToAttach.getPaneID());
                attachedPicturePaneTableCollectionNew.add(picturePaneTableCollectionNewPicturePaneTableToAttach);
            }
            picturePaneTableCollectionNew = attachedPicturePaneTableCollectionNew;
            picturePaneTable.setPicturePaneTableCollection(picturePaneTableCollectionNew);
            Collection<PictureButtonTable> attachedPictureButtonTableCollectionNew = new ArrayList<PictureButtonTable>();
            for (PictureButtonTable pictureButtonTableCollectionNewPictureButtonTableToAttach : pictureButtonTableCollectionNew) {
                pictureButtonTableCollectionNewPictureButtonTableToAttach = em.getReference(pictureButtonTableCollectionNewPictureButtonTableToAttach.getClass(), pictureButtonTableCollectionNewPictureButtonTableToAttach.getButtonID());
                attachedPictureButtonTableCollectionNew.add(pictureButtonTableCollectionNewPictureButtonTableToAttach);
            }
            pictureButtonTableCollectionNew = attachedPictureButtonTableCollectionNew;
            picturePaneTable.setPictureButtonTableCollection(pictureButtonTableCollectionNew);
            picturePaneTable = em.merge(picturePaneTable);
            if (parentframeIDOld != null && !parentframeIDOld.equals(parentframeIDNew)) {
                parentframeIDOld.getPicturePaneTableCollection().remove(picturePaneTable);
                parentframeIDOld = em.merge(parentframeIDOld);
            }
            if (parentframeIDNew != null && !parentframeIDNew.equals(parentframeIDOld)) {
                parentframeIDNew.getPicturePaneTableCollection().add(picturePaneTable);
                parentframeIDNew = em.merge(parentframeIDNew);
            }
            if (parentpaneIDOld != null && !parentpaneIDOld.equals(parentpaneIDNew)) {
                parentpaneIDOld.getPicturePaneTableCollection().remove(picturePaneTable);
                parentpaneIDOld = em.merge(parentpaneIDOld);
            }
            if (parentpaneIDNew != null && !parentpaneIDNew.equals(parentpaneIDOld)) {
                parentpaneIDNew.getPicturePaneTableCollection().add(picturePaneTable);
                parentpaneIDNew = em.merge(parentpaneIDNew);
            }
            for (PictureComponentTable pictureComponentTableCollectionNewPictureComponentTable : pictureComponentTableCollectionNew) {
                if (!pictureComponentTableCollectionOld.contains(pictureComponentTableCollectionNewPictureComponentTable)) {
                    PicturePaneTable oldParentpaneIDOfPictureComponentTableCollectionNewPictureComponentTable = pictureComponentTableCollectionNewPictureComponentTable.getParentpaneID();
                    pictureComponentTableCollectionNewPictureComponentTable.setParentpaneID(picturePaneTable);
                    pictureComponentTableCollectionNewPictureComponentTable = em.merge(pictureComponentTableCollectionNewPictureComponentTable);
                    if (oldParentpaneIDOfPictureComponentTableCollectionNewPictureComponentTable != null && !oldParentpaneIDOfPictureComponentTableCollectionNewPictureComponentTable.equals(picturePaneTable)) {
                        oldParentpaneIDOfPictureComponentTableCollectionNewPictureComponentTable.getPictureComponentTableCollection().remove(pictureComponentTableCollectionNewPictureComponentTable);
                        oldParentpaneIDOfPictureComponentTableCollectionNewPictureComponentTable = em.merge(oldParentpaneIDOfPictureComponentTableCollectionNewPictureComponentTable);
                    }
                }
            }
            for (PicturePaneTable picturePaneTableCollectionOldPicturePaneTable : picturePaneTableCollectionOld) {
                if (!picturePaneTableCollectionNew.contains(picturePaneTableCollectionOldPicturePaneTable)) {
                    picturePaneTableCollectionOldPicturePaneTable.setParentpaneID(null);
                    picturePaneTableCollectionOldPicturePaneTable = em.merge(picturePaneTableCollectionOldPicturePaneTable);
                }
            }
            for (PicturePaneTable picturePaneTableCollectionNewPicturePaneTable : picturePaneTableCollectionNew) {
                if (!picturePaneTableCollectionOld.contains(picturePaneTableCollectionNewPicturePaneTable)) {
                    PicturePaneTable oldParentpaneIDOfPicturePaneTableCollectionNewPicturePaneTable = picturePaneTableCollectionNewPicturePaneTable.getParentpaneID();
                    picturePaneTableCollectionNewPicturePaneTable.setParentpaneID(picturePaneTable);
                    picturePaneTableCollectionNewPicturePaneTable = em.merge(picturePaneTableCollectionNewPicturePaneTable);
                    if (oldParentpaneIDOfPicturePaneTableCollectionNewPicturePaneTable != null && !oldParentpaneIDOfPicturePaneTableCollectionNewPicturePaneTable.equals(picturePaneTable)) {
                        oldParentpaneIDOfPicturePaneTableCollectionNewPicturePaneTable.getPicturePaneTableCollection().remove(picturePaneTableCollectionNewPicturePaneTable);
                        oldParentpaneIDOfPicturePaneTableCollectionNewPicturePaneTable = em.merge(oldParentpaneIDOfPicturePaneTableCollectionNewPicturePaneTable);
                    }
                }
            }
            for (PictureButtonTable pictureButtonTableCollectionNewPictureButtonTable : pictureButtonTableCollectionNew) {
                if (!pictureButtonTableCollectionOld.contains(pictureButtonTableCollectionNewPictureButtonTable)) {
                    PicturePaneTable oldParentpaneIDOfPictureButtonTableCollectionNewPictureButtonTable = pictureButtonTableCollectionNewPictureButtonTable.getParentpaneID();
                    pictureButtonTableCollectionNewPictureButtonTable.setParentpaneID(picturePaneTable);
                    pictureButtonTableCollectionNewPictureButtonTable = em.merge(pictureButtonTableCollectionNewPictureButtonTable);
                    if (oldParentpaneIDOfPictureButtonTableCollectionNewPictureButtonTable != null && !oldParentpaneIDOfPictureButtonTableCollectionNewPictureButtonTable.equals(picturePaneTable)) {
                        oldParentpaneIDOfPictureButtonTableCollectionNewPictureButtonTable.getPictureButtonTableCollection().remove(pictureButtonTableCollectionNewPictureButtonTable);
                        oldParentpaneIDOfPictureButtonTableCollectionNewPictureButtonTable = em.merge(oldParentpaneIDOfPictureButtonTableCollectionNewPictureButtonTable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = picturePaneTable.getPaneID();
                if (findPicturePaneTable(id) == null) {
                    throw new NonexistentEntityException("The picturePaneTable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PicturePaneTable picturePaneTable;
            try {
                picturePaneTable = em.getReference(PicturePaneTable.class, id);
                picturePaneTable.getPaneID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The picturePaneTable with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PictureComponentTable> pictureComponentTableCollectionOrphanCheck = picturePaneTable.getPictureComponentTableCollection();
            for (PictureComponentTable pictureComponentTableCollectionOrphanCheckPictureComponentTable : pictureComponentTableCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PicturePaneTable (" + picturePaneTable + ") cannot be destroyed since the PictureComponentTable " + pictureComponentTableCollectionOrphanCheckPictureComponentTable + " in its pictureComponentTableCollection field has a non-nullable parentpaneID field.");
            }
            Collection<PictureButtonTable> pictureButtonTableCollectionOrphanCheck = picturePaneTable.getPictureButtonTableCollection();
            for (PictureButtonTable pictureButtonTableCollectionOrphanCheckPictureButtonTable : pictureButtonTableCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PicturePaneTable (" + picturePaneTable + ") cannot be destroyed since the PictureButtonTable " + pictureButtonTableCollectionOrphanCheckPictureButtonTable + " in its pictureButtonTableCollection field has a non-nullable parentpaneID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PictureFrameTable parentframeID = picturePaneTable.getParentframeID();
            if (parentframeID != null) {
                parentframeID.getPicturePaneTableCollection().remove(picturePaneTable);
                parentframeID = em.merge(parentframeID);
            }
            PicturePaneTable parentpaneID = picturePaneTable.getParentpaneID();
            if (parentpaneID != null) {
                parentpaneID.getPicturePaneTableCollection().remove(picturePaneTable);
                parentpaneID = em.merge(parentpaneID);
            }
            Collection<PicturePaneTable> picturePaneTableCollection = picturePaneTable.getPicturePaneTableCollection();
            for (PicturePaneTable picturePaneTableCollectionPicturePaneTable : picturePaneTableCollection) {
                picturePaneTableCollectionPicturePaneTable.setParentpaneID(null);
                picturePaneTableCollectionPicturePaneTable = em.merge(picturePaneTableCollectionPicturePaneTable);
            }
            em.remove(picturePaneTable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PicturePaneTable> findPicturePaneTableEntities() {
        return findPicturePaneTableEntities(true, -1, -1);
    }

    public List<PicturePaneTable> findPicturePaneTableEntities(int maxResults, int firstResult) {
        return findPicturePaneTableEntities(false, maxResults, firstResult);
    }

    private List<PicturePaneTable> findPicturePaneTableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PicturePaneTable.class));
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

    public PicturePaneTable findPicturePaneTable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PicturePaneTable.class, id);
        } finally {
            em.close();
        }
    }

    public int getPicturePaneTableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PicturePaneTable> rt = cq.from(PicturePaneTable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
