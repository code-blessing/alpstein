# Building block 2: The essential data model

The `essential-model-data` Gradle module describes what this concrete project is about — in the
shipped example, the DVD-rental [sakila](./database.md) data model (`Actor`, `Film`, `Customer`, `Rental`, ...).

This description is written as a Kotlin DSL built on top of [*SourceAmazing*](https://github.com/code-blessing/sourceamazing):

```kotlin
item(itemId = Items.FILM) {
    attribute(name = "FilmId").primaryKey(type = PrimaryKeyType.NUMBER)
    attribute(name = "Title").string()
    attribute(name = "Language").reference(itemId = Items.LANGUAGE)
    attribute(name = "Rating").enumType(enumId = EnumTypes.MPAA_RATING).options(nullable = true)
    ...
}
```

Each entity is called an *item* in the model. For each item, the description captures the
essential, domain-level information:

* its attributes, their types (string, number, date, boolean, reference, enum, ...) and options
  (nullable, occurs multiple times, ...)
* which items are referenced and which values an enum has
* how an item is presented in the UI (which columns the result table shows, how the editor is
  laid out)
* which table and columns an item maps to in the database

The description lives in a single file,
`essential-model-data/src/main/kotlin/org/codeblessing/alpstein/model/EssentialModelData.kt`,
and keeps these concerns apart: `collectItemData()` declares the items, attributes, references and
enums, `collectUiData()` the presentation, and `collectDatabaseData()` the table and column mapping.

This module contains **no** logic about controllers, repositories, or UI components — only the
plain domain data. That is exactly what makes it swappable: replace the description with that of
a different domain, and neither of the other two building blocks needs to change.

## Where SourceAmazing fits in

**[SourceAmazing](https://github.com/code-blessing/sourceamazing)** provides the DSL that `essential-model-data`
uses to declare the essential data (`schema { item(...) { attribute(...) } }`), and turns that
declaration into Kotlin data classes — a typed, queryable runtime model. That model is what the renderers of
[building block 3](building-block-3-the-code-generation.md) iterate over during generation (`model.items`, `attribute.attributeName.pascalCase`, ...).
