package authoring_backend.entities;

public interface CRUDInterface {
    void createSprite();
    void createTile();
    void read(int id);
    void update(Entity entity);
    void delete(Entity entity);
}
