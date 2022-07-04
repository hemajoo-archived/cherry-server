/*
 * (C) Copyright Hemajoo Systems Inc.  2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.server.base.mapper;

import com.hemajoo.commerce.cherry.server.data.model.base.ServerEntity;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.exception.EntityException;
import com.hemajoo.commerce.cherry.server.shared.data.model.entity.base.identity.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;

/**
 * Abstract mapper used to map between instances of entity identities to server entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper
public abstract class AbstractEntityMapper
{
    /**
     * Instance to this bean mapper.
     */
    public static final AbstractEntityMapper INSTANCE = Mappers.getMapper(AbstractEntityMapper.class);

    /**
     * Maps an entity identity to a server base entity.
     * <hr>
     * If the base entity exist in the underlying database, it will be loaded and returned, otherwise an exception is raised.
     * @param <T> Type of server entity.
     * @param identity Entity identity.
     * @param entityManager Entity manager.
     * @return Server base entity.
     * @throws EntityException Thrown in case an error occurred while trying to retrieve the entity from the underlying repository.
     */
    public <T extends ServerEntity> T map(Identity identity, @Context EntityManager entityManager) throws EntityException
    {
        ServerEntity entity;

        if (identity != null)
        {
            entity = entityManager.find(ServerEntity.class, identity.getId());
            if (entity == null)
            {
                throw new EntityException(String.format("Server entity with identity: %s cannot be found!", identity));
            }

            return (T) entity;
        }

        return null;
    }
}

