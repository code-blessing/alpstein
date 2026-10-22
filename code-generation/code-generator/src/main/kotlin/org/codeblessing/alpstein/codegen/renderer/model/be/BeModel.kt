package org.codeblessing.alpstein.codegen.renderer.model.be

data class BeModel(
    val items: List<BeItemModel>,
    val enums: List<BeEnumModel>,
) {
    /**
     * The items that are identified by a primary key. Only for those the whole stack
     * (controller, service, repository, search, by-ids, example data initializer) exists,
     * because only they can be addressed, searched and referenced.
     */
    val itemsWithPrimaryKey: List<BeItemModel> = BeItemModel.sortedByDependencies(items.filter { it.hasPrimaryKey })
}
