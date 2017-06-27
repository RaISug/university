package com.radoslav.javaee.courses.db.accessors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.radoslav.javaee.courses.entities.EntityType;
import com.radoslav.javaee.courses.entities.InMemoryEntity;
import com.radoslav.javaee.courses.entities.PersistentEntity;

public class InMemoryAccessor implements PersistenceAccessor {

  private static Map<EntityType, Map<String, InMemoryEntity>> storage = new HashMap<>();

  @Override
  public void insert(PersistentEntity persistenceEntity) {
    validateEntityType(persistenceEntity);

    InMemoryEntity inMemoryEntity = (InMemoryEntity) persistenceEntity;

    Map<String, InMemoryEntity> entities = storage.get(inMemoryEntity.getType());
    if (entities == null) {
      entities = new HashMap<String, InMemoryEntity>();
    }

    entities.put(inMemoryEntity.getIdentifier(), inMemoryEntity);

    storage.put(inMemoryEntity.getType(), entities);
  }

  private void validateEntityType(PersistentEntity persistenceEntity) {
    if (InMemoryEntity.class.isAssignableFrom(persistenceEntity.getClass())) {
      return;
    }

    throw new IllegalArgumentException("Provided entity is not supported.");
  }

  @Override
  public void update(PersistentEntity entity) {
    throw new UnsupportedOperationException("Update of entity is not available in current releasion of the API.");
  }

  @Override
  public void delete(PersistentEntity entity) {
    throw new UnsupportedOperationException("Deletion of entity is not available in current releasion of the API.");
  }

  @Override
  public Set<? extends PersistentEntity> selectAll(EntityType entityType) {
    Map<String, InMemoryEntity> entities = storage.get(entityType);
    if (entities == null) {
      return new HashSet<>();
    }

    return new HashSet<InMemoryEntity>(entities.values());
  }

  @Override
  public PersistentEntity select(EntityType entityType, String identifier) {
    Map<String, InMemoryEntity> entities = storage.get(entityType);

    return entities == null ? null : entities.get(identifier);
  }

}
