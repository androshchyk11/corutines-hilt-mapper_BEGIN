package ua.oleksii.fitplantest.utils.mappers.abstraction

interface Mapper<ResponseEntity, Entity> {

    fun responseEntityToUiEntity(domainModel: ResponseEntity): Entity

    fun mapFromEntityList(entities: List<ResponseEntity>): List<Entity> {
        return entities.map { e -> responseEntityToUiEntity(e) }
    }
}