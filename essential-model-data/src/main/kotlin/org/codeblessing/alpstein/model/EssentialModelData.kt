package org.codeblessing.alpstein.model

import org.codeblessing.alpstein.model.builders.RootDsl
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.ExampleDataCategory
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.PrimaryKeyType

object EssentialModelData {
    fun RootDsl.collectData() {
        collectItemData()
        collectUiData()
        collectDatabaseData()
    }

    enum class Items(
        override val itemName: String,
    ) : ItemId {
        ACTOR(itemName = "Actor"),
        CATEGORY(itemName = "Category"),
        FILM(itemName = "Film"),
        FILM_ACTOR(itemName = "FilmActor"),
        FILM_CATEGORY(itemName = "FilmCategory"),
        ADDRESS(itemName = "Address"),
        CITY(itemName = "City"),
        COUNTRY(itemName = "Country"),
        CUSTOMER(itemName = "Customer"),
        INVENTORY(itemName = "Inventory"),
        LANGUAGE(itemName = "Language"),
        PAYMENT(itemName = "Payment"),
        RENTAL(itemName = "Rental"),
        STAFF(itemName = "Staff"),
        STORE(itemName = "Store"),
    }

    enum class EnumTypes(
        override val enumName: String,
    ) : EnumId {
        MPAA_RATING(enumName = "MpaaRating"),
    }

    private fun RootDsl.collectItemData() {
        schema {
            enumType(enumId = EnumTypes.MPAA_RATING) {
                enumValue(name = "G")
                enumValue(name = "PG")
                // 'PG-13' and 'NC-17' lose their hyphen: an enum value has to be in PascalCase.
                // The hyphen is restored by the `dbEnum` in collectDatabaseData(), which is what
                // the value is stored as.
                enumValue(name = "PG13")
                enumValue(name = "R")
                enumValue(name = "NC17")
            }

            item(itemId = Items.ACTOR) {
                attribute(name = "ActorId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "FirstName").string(exampleDataCategory = ExampleDataCategory.FIRSTNAME)
                attribute(name = "LastName").string(exampleDataCategory = ExampleDataCategory.LASTNAME)
            }

            item(itemId = Items.CATEGORY) {
                attribute(name = "CategoryId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Name").string()
            }

            item(itemId = Items.FILM) {
                attribute(name = "FilmId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Title").string()
                attribute(name = "Description").string().options(nullable = true)
                attribute(name = "ReleaseYear").number().options(nullable = true)
                attribute(name = "Language").reference(itemId = Items.LANGUAGE)
                attribute(name = "OriginalLanguage").reference(itemId = Items.LANGUAGE).options(nullable = true)
                attribute(name = "RentalDuration").number()
                attribute(name = "RentalRate").double(exampleDataCategory = ExampleDataCategory.AMOUNT)
                attribute(name = "Length").number().options(nullable = true)
                attribute(name = "ReplacementCost").double(exampleDataCategory = ExampleDataCategory.AMOUNT)
                attribute(name = "Rating").enumType(enumId = EnumTypes.MPAA_RATING).options(nullable = true)
                attribute(name = "SpecialFeatures").string().options(nullable = true, multiple = true)
            }

            item(itemId = Items.FILM_ACTOR) {
                // The table is identified by (actor_id, film_id); an item is identified by one
                // single attribute, therefore the link table gets a surrogate key of its own.
                attribute(name = "FilmActorId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Actor").reference(itemId = Items.ACTOR)
                attribute(name = "Film").reference(itemId = Items.FILM)
            }

            item(itemId = Items.FILM_CATEGORY) {
                // Surrogate key for the composite (film_id, category_id), see FilmActor.
                attribute(name = "FilmCategoryId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Film").reference(itemId = Items.FILM)
                attribute(name = "Category").reference(itemId = Items.CATEGORY)
            }

            item(itemId = Items.ADDRESS) {
                attribute(name = "AddressId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Address").string(exampleDataCategory = ExampleDataCategory.STREET)
                attribute(name = "Address2").string(exampleDataCategory = ExampleDataCategory.STREET).options(nullable = true)
                attribute(name = "District").string()
                attribute(name = "City").reference(itemId = Items.CITY)
                attribute(name = "PostalCode").string(exampleDataCategory = ExampleDataCategory.POSTCODE).options(nullable = true)
                attribute(name = "Phone").string()
            }

            item(itemId = Items.CITY) {
                attribute(name = "CityId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "City").string(exampleDataCategory = ExampleDataCategory.CITY)
                attribute(name = "Country").reference(itemId = Items.COUNTRY)
            }

            item(itemId = Items.COUNTRY) {
                attribute(name = "CountryId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Country").string(exampleDataCategory = ExampleDataCategory.COUNTRY_NAME)
            }

            item(itemId = Items.CUSTOMER) {
                attribute(name = "CustomerId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Store").reference(itemId = Items.STORE)
                attribute(name = "FirstName").string(exampleDataCategory = ExampleDataCategory.FIRSTNAME)
                attribute(name = "LastName").string(exampleDataCategory = ExampleDataCategory.LASTNAME)
                attribute(name = "Email").string().options(nullable = true)
                attribute(name = "Address").reference(itemId = Items.ADDRESS)
                attribute(name = "Activebool").boolean()
                // A DATE has no built-in type of its own, therefore it is carried as text.
                attribute(name = "CreateDate").string()
                attribute(name = "Active").number().options(nullable = true)
            }

            item(itemId = Items.INVENTORY) {
                attribute(name = "InventoryId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Film").reference(itemId = Items.FILM)
                attribute(name = "Store").reference(itemId = Items.STORE)
            }

            item(itemId = Items.LANGUAGE) {
                attribute(name = "LanguageId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Name").string()
            }

            item(itemId = Items.PAYMENT) {
                attribute(name = "PaymentId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Customer").reference(itemId = Items.CUSTOMER)
                attribute(name = "Staff").reference(itemId = Items.STAFF)
                attribute(name = "Rental").reference(itemId = Items.RENTAL)
                attribute(name = "Amount").double(exampleDataCategory = ExampleDataCategory.AMOUNT)
            }

            item(itemId = Items.RENTAL) {
                attribute(name = "RentalId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "Inventory").reference(itemId = Items.INVENTORY)
                attribute(name = "Customer").reference(itemId = Items.CUSTOMER)
                attribute(name = "Staff").reference(itemId = Items.STAFF)
            }

            item(itemId = Items.STAFF) {
                attribute(name = "StaffId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "FirstName").string(exampleDataCategory = ExampleDataCategory.FIRSTNAME)
                attribute(name = "LastName").string(exampleDataCategory = ExampleDataCategory.LASTNAME)
                attribute(name = "Address").reference(itemId = Items.ADDRESS)
                attribute(name = "Email").string().options(nullable = true)
                attribute(name = "Store").reference(itemId = Items.STORE)
                attribute(name = "Active").boolean()
                attribute(name = "Username").string()
                attribute(name = "Password").string().options(nullable = true)
                // A BYTEA has no built-in type of its own, therefore it is carried as text.
                attribute(name = "Picture").string().options(nullable = true)
            }

            item(itemId = Items.STORE) {
                attribute(name = "StoreId").primaryKey(type = PrimaryKeyType.NUMBER)
                attribute(name = "ManagerStaff").reference(itemId = Items.STAFF)
                attribute(name = "Address").reference(itemId = Items.ADDRESS)
            }
        }
    }

    private fun RootDsl.collectDatabaseData() {
        schema {
            dbEnum(enumId = EnumTypes.MPAA_RATING) {
                enumTypeName(name = "mpaa_rating")
                enumValue(name = "G", databaseValue = "G")
                enumValue(name = "PG", databaseValue = "PG")
                enumValue(name = "PG13", databaseValue = "PG-13")
                enumValue(name = "R", databaseValue = "R")
                enumValue(name = "NC17", databaseValue = "NC-17")
            }

            dbItem(itemId = Items.ACTOR) {
                tableName(name = "actor")
                column(attributeName = "ActorId", columnName = "actor_id")
                column(attributeName = "FirstName", columnName = "first_name")
                column(attributeName = "LastName", columnName = "last_name")
            }

            dbItem(itemId = Items.CATEGORY) {
                tableName(name = "category")
                column(attributeName = "CategoryId", columnName = "category_id")
                column(attributeName = "Name", columnName = "name")
            }

            dbItem(itemId = Items.FILM) {
                tableName(name = "film")
                column(attributeName = "FilmId", columnName = "film_id")
                column(attributeName = "Title", columnName = "title")
                column(attributeName = "Description", columnName = "description")
                column(attributeName = "ReleaseYear", columnName = "release_year")
                column(attributeName = "Language", columnName = "language_id")
                column(attributeName = "OriginalLanguage", columnName = "original_language_id")
                column(attributeName = "RentalDuration", columnName = "rental_duration")
                column(attributeName = "RentalRate", columnName = "rental_rate")
                column(attributeName = "Length", columnName = "length")
                column(attributeName = "ReplacementCost", columnName = "replacement_cost")
                column(attributeName = "Rating", columnName = "rating")
                column(attributeName = "SpecialFeatures", columnName = "special_features")
            }

            dbItem(itemId = Items.FILM_ACTOR) {
                tableName(name = "film_actor")
                column(attributeName = "FilmActorId", columnName = "film_actor_id")
                column(attributeName = "Actor", columnName = "actor_id")
                column(attributeName = "Film", columnName = "film_id")
            }

            dbItem(itemId = Items.FILM_CATEGORY) {
                tableName(name = "film_category")
                column(attributeName = "FilmCategoryId", columnName = "film_category_id")
                column(attributeName = "Film", columnName = "film_id")
                column(attributeName = "Category", columnName = "category_id")
            }

            dbItem(itemId = Items.ADDRESS) {
                tableName(name = "address")
                column(attributeName = "AddressId", columnName = "address_id")
                column(attributeName = "Address", columnName = "address")
                column(attributeName = "Address2", columnName = "address2")
                column(attributeName = "District", columnName = "district")
                column(attributeName = "City", columnName = "city_id")
                column(attributeName = "PostalCode", columnName = "postal_code")
                column(attributeName = "Phone", columnName = "phone")
            }

            dbItem(itemId = Items.CITY) {
                tableName(name = "city")
                column(attributeName = "CityId", columnName = "city_id")
                column(attributeName = "City", columnName = "city")
                column(attributeName = "Country", columnName = "country_id")
            }

            dbItem(itemId = Items.COUNTRY) {
                tableName(name = "country")
                column(attributeName = "CountryId", columnName = "country_id")
                column(attributeName = "Country", columnName = "country")
            }

            dbItem(itemId = Items.CUSTOMER) {
                tableName(name = "customer")
                column(attributeName = "CustomerId", columnName = "customer_id")
                column(attributeName = "Store", columnName = "store_id")
                column(attributeName = "FirstName", columnName = "first_name")
                column(attributeName = "LastName", columnName = "last_name")
                column(attributeName = "Email", columnName = "email")
                column(attributeName = "Address", columnName = "address_id")
                column(attributeName = "Activebool", columnName = "activebool")
                column(attributeName = "CreateDate", columnName = "create_date")
                column(attributeName = "Active", columnName = "active")
            }

            dbItem(itemId = Items.INVENTORY) {
                tableName(name = "inventory")
                column(attributeName = "InventoryId", columnName = "inventory_id")
                column(attributeName = "Film", columnName = "film_id")
                column(attributeName = "Store", columnName = "store_id")
            }

            dbItem(itemId = Items.LANGUAGE) {
                tableName(name = "language")
                column(attributeName = "LanguageId", columnName = "language_id")
                column(attributeName = "Name", columnName = "name")
            }

            dbItem(itemId = Items.PAYMENT) {
                tableName(name = "payment")
                column(attributeName = "PaymentId", columnName = "payment_id")
                column(attributeName = "Customer", columnName = "customer_id")
                column(attributeName = "Staff", columnName = "staff_id")
                column(attributeName = "Rental", columnName = "rental_id")
                column(attributeName = "Amount", columnName = "amount")
            }

            dbItem(itemId = Items.RENTAL) {
                tableName(name = "rental")
                column(attributeName = "RentalId", columnName = "rental_id")
                column(attributeName = "Inventory", columnName = "inventory_id")
                column(attributeName = "Customer", columnName = "customer_id")
                column(attributeName = "Staff", columnName = "staff_id")
            }

            dbItem(itemId = Items.STAFF) {
                tableName(name = "staff")
                column(attributeName = "StaffId", columnName = "staff_id")
                column(attributeName = "FirstName", columnName = "first_name")
                column(attributeName = "LastName", columnName = "last_name")
                column(attributeName = "Address", columnName = "address_id")
                column(attributeName = "Email", columnName = "email")
                column(attributeName = "Store", columnName = "store_id")
                column(attributeName = "Active", columnName = "active")
                column(attributeName = "Username", columnName = "username")
                column(attributeName = "Password", columnName = "password")
                column(attributeName = "Picture", columnName = "picture")
            }

            dbItem(itemId = Items.STORE) {
                tableName(name = "store")
                column(attributeName = "StoreId", columnName = "store_id")
                column(attributeName = "ManagerStaff", columnName = "manager_staff_id")
                column(attributeName = "Address", columnName = "address_id")
            }
        }
    }

    private fun RootDsl.collectUiData() {
        schema {
            uiItem(itemId = Items.ACTOR) {
                displayAttributes {
                    attribute(attributeName = "FirstName")
                    attribute(attributeName = "LastName")
                }
            }
            uiItem(itemId = Items.CATEGORY) {
                displayAttributes {
                    attribute(attributeName = "Name")
                }
            }
            uiItem(itemId = Items.FILM) {
                displayAttributes {
                    attribute(attributeName = "Title")
                }
            }
            // A link table holds nothing but foreign keys, so its references are all it can be
            // shown by. The same holds for Inventory, Payment, Rental and Store.
            uiItem(itemId = Items.FILM_ACTOR) {
                displayAttributes {
                    attribute(attributeName = "Actor")
                    attribute(attributeName = "Film")
                }
            }
            uiItem(itemId = Items.FILM_CATEGORY) {
                displayAttributes {
                    attribute(attributeName = "Film")
                    attribute(attributeName = "Category")
                }
            }
            uiItem(itemId = Items.ADDRESS) {
                displayAttributes {
                    attribute(attributeName = "Address")
                    attribute(attributeName = "District")
                }
            }
            uiItem(itemId = Items.CITY) {
                displayAttributes {
                    attribute(attributeName = "City")
                }
            }
            uiItem(itemId = Items.COUNTRY) {
                displayAttributes {
                    attribute(attributeName = "Country")
                }
            }
            uiItem(itemId = Items.CUSTOMER) {
                displayAttributes {
                    attribute(attributeName = "FirstName")
                    attribute(attributeName = "LastName")
                }
            }
            uiItem(itemId = Items.INVENTORY) {
                displayAttributes {
                    attribute(attributeName = "Film")
                    attribute(attributeName = "Store")
                }
            }
            uiItem(itemId = Items.LANGUAGE) {
                displayAttributes {
                    attribute(attributeName = "Name")
                }
            }
            uiItem(itemId = Items.PAYMENT) {
                displayAttributes {
                    attribute(attributeName = "Customer")
                    attribute(attributeName = "Rental")
                }
            }
            uiItem(itemId = Items.RENTAL) {
                displayAttributes {
                    attribute(attributeName = "Inventory")
                    attribute(attributeName = "Customer")
                }
            }
            uiItem(itemId = Items.STAFF) {
                displayAttributes {
                    attribute(attributeName = "FirstName")
                    attribute(attributeName = "LastName")
                }
            }
            uiItem(itemId = Items.STORE) {
                displayAttributes {
                    attribute(attributeName = "ManagerStaff")
                    attribute(attributeName = "Address")
                }
            }

            uiEntity(uiEntityName = "Actor", rootItemId = Items.ACTOR) {
                views {
                    searchResult {
                        attribute(attributeName = "ActorId")
                        attribute(attributeName = "FirstName")
                        attribute(attributeName = "LastName")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "ActorId")
                                    attribute(attributeName = "FirstName")
                                    attribute(attributeName = "LastName")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Category", rootItemId = Items.CATEGORY) {
                views {
                    searchResult {
                        attribute(attributeName = "CategoryId")
                        attribute(attributeName = "Name")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "CategoryId")
                                    attribute(attributeName = "Name")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Film", rootItemId = Items.FILM) {
                views {
                    searchResult {
                        attribute(attributeName = "FilmId")
                        attribute(attributeName = "Title")
                        attribute(attributeName = "Description")
                        attribute(attributeName = "ReleaseYear")
                        attribute(attributeName = "Language")
                        attribute(attributeName = "RentalRate")
                        attribute(attributeName = "Length")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "FilmId")
                                    section(sectionTranslationKey = "film.section.basic")
                                    attribute(attributeName = "Title")
                                    attribute(attributeName = "Description")
                                    attribute(attributeName = "ReleaseYear")
                                }
                            }
                            tab(tabTranslationKey = "tab.film.details") {
                                column {
                                    section(sectionTranslationKey = "film.section.language")
                                    attribute(attributeName = "Language")
                                    attribute(attributeName = "OriginalLanguage")
                                    section(sectionTranslationKey = "film.section.rating")
                                    attribute(attributeName = "Rating")
                                    attribute(attributeName = "SpecialFeatures")
                                }
                                column {
                                    attribute(attributeName = "RentalDuration")
                                    attribute(attributeName = "RentalRate")
                                    attribute(attributeName = "Length")
                                    attribute(attributeName = "ReplacementCost")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "FilmActor", rootItemId = Items.FILM_ACTOR) {
                views {
                    searchResult {
                        attribute(attributeName = "FilmActorId")
                        attribute(attributeName = "Actor")
                        attribute(attributeName = "Film")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "FilmActorId")
                                    attribute(attributeName = "Actor")
                                    attribute(attributeName = "Film")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "FilmCategory", rootItemId = Items.FILM_CATEGORY) {
                views {
                    searchResult {
                        attribute(attributeName = "FilmCategoryId")
                        attribute(attributeName = "Film")
                        attribute(attributeName = "Category")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "FilmCategoryId")
                                    attribute(attributeName = "Film")
                                    attribute(attributeName = "Category")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Address", rootItemId = Items.ADDRESS) {
                views {
                    searchResult {
                        attribute(attributeName = "AddressId")
                        attribute(attributeName = "Address")
                        attribute(attributeName = "Address2")
                        attribute(attributeName = "District")
                        attribute(attributeName = "City")
                        attribute(attributeName = "PostalCode")
                        attribute(attributeName = "Phone")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "AddressId")
                                    attribute(attributeName = "Address")
                                    attribute(attributeName = "Address2")
                                    attribute(attributeName = "District")
                                    attribute(attributeName = "City")
                                    attribute(attributeName = "PostalCode")
                                    attribute(attributeName = "Phone")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "City", rootItemId = Items.CITY) {
                views {
                    searchResult {
                        attribute(attributeName = "CityId")
                        attribute(attributeName = "City")
                        attribute(attributeName = "Country")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "CityId")
                                    attribute(attributeName = "City")
                                    attribute(attributeName = "Country")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Country", rootItemId = Items.COUNTRY) {
                views {
                    searchResult {
                        attribute(attributeName = "CountryId")
                        attribute(attributeName = "Country")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "CountryId")
                                    attribute(attributeName = "Country")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Customer", rootItemId = Items.CUSTOMER) {
                views {
                    searchResult {
                        attribute(attributeName = "CustomerId")
                        attribute(attributeName = "Store")
                        attribute(attributeName = "FirstName")
                        attribute(attributeName = "LastName")
                        attribute(attributeName = "Email")
                        attribute(attributeName = "Address")
                        attribute(attributeName = "Activebool")
                        attribute(attributeName = "CreateDate")
                        attribute(attributeName = "Active")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "CustomerId")
                                    attribute(attributeName = "Store")
                                    attribute(attributeName = "FirstName")
                                    attribute(attributeName = "LastName")
                                    attribute(attributeName = "Email")
                                    attribute(attributeName = "Address")
                                    attribute(attributeName = "Activebool")
                                    attribute(attributeName = "CreateDate")
                                    attribute(attributeName = "Active")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Inventory", rootItemId = Items.INVENTORY) {
                views {
                    searchResult {
                        attribute(attributeName = "InventoryId")
                        attribute(attributeName = "Film")
                        attribute(attributeName = "Store")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "InventoryId")
                                    attribute(attributeName = "Film")
                                    attribute(attributeName = "Store")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Language", rootItemId = Items.LANGUAGE) {
                views {
                    searchResult {
                        attribute(attributeName = "LanguageId")
                        attribute(attributeName = "Name")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "LanguageId")
                                    attribute(attributeName = "Name")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Payment", rootItemId = Items.PAYMENT) {
                views {
                    searchResult {
                        attribute(attributeName = "PaymentId")
                        attribute(attributeName = "Customer")
                        attribute(attributeName = "Staff")
                        attribute(attributeName = "Rental")
                        attribute(attributeName = "Amount")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "PaymentId")
                                    attribute(attributeName = "Customer")
                                    attribute(attributeName = "Staff")
                                    attribute(attributeName = "Rental")
                                    attribute(attributeName = "Amount")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Rental", rootItemId = Items.RENTAL) {
                views {
                    searchResult {
                        attribute(attributeName = "RentalId")
                        attribute(attributeName = "Inventory")
                        attribute(attributeName = "Customer")
                        attribute(attributeName = "Staff")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "RentalId")
                                    attribute(attributeName = "Inventory")
                                    attribute(attributeName = "Customer")
                                    attribute(attributeName = "Staff")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Staff", rootItemId = Items.STAFF) {
                views {
                    searchResult {
                        attribute(attributeName = "StaffId")
                        attribute(attributeName = "FirstName")
                        attribute(attributeName = "LastName")
                        attribute(attributeName = "Address")
                        attribute(attributeName = "Email")
                        attribute(attributeName = "Store")
                        attribute(attributeName = "Active")
                        attribute(attributeName = "Username")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "StaffId")
                                    attribute(attributeName = "FirstName")
                                    attribute(attributeName = "LastName")
                                    attribute(attributeName = "Address")
                                    attribute(attributeName = "Email")
                                    attribute(attributeName = "Store")
                                    attribute(attributeName = "Active")
                                    attribute(attributeName = "Username")
                                    attribute(attributeName = "Password")
                                    attribute(attributeName = "Picture")
                                }
                            }
                        }
                    }
                }
            }

            uiEntity(uiEntityName = "Store", rootItemId = Items.STORE) {
                views {
                    searchResult {
                        attribute(attributeName = "StoreId")
                        attribute(attributeName = "ManagerStaff")
                        attribute(attributeName = "Address")
                    }
                    editor {
                        configureEditorForMainItem {
                            tab(tabTranslationKey = "tab.common") {
                                column {
                                    attribute(attributeName = "StoreId")
                                    attribute(attributeName = "ManagerStaff")
                                    attribute(attributeName = "Address")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
