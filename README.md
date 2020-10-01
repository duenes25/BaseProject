# This is a Basic Project that initially just demonstrated usage of a RecyclerView.  But morphed into more as shown Below

## Demonstrated how to use a viewmodel and mutableLiveData as a reactive method to inform the user when Network connectovoty is lost
1. Use connectovity manager to register network call back    
    connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
2. Detect the change from the callback, and viewModel will post the new value.  
    var networkCallback: NetworkCallback = object : NetworkCallback() {  
        override fun onAvailable(network: Network) { ... }  
        override fun onLost(network: Network) { ... }  
    }  
    3. Any Observers will be able to recat to this network change   

## Demonstrated how to include and use the logging Library from JitPack  
## The project is in another Repo, and loaded up into jitpack.   
1. Include Jitpack repo in the module build.gradle  
    repositories {    
        ...
        maven { url 'https://jitpack.io' }
    }
2. Added the dependency  in the app build.gradle
    implementation 'com.github.duenes25:LoggingLibrary:0.0.1'  
    
3. Use the function from the Library  
    logIt()



## Demonstrates how to use Room DB and DAO pattern
1. Add the necessary dependencies  

    dependencies {  
        implementation "androidx.room:room-runtime:$room_version"  
        implementation "androidx.legacy:legacy-support-v4:1.0.0"  
        implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0'  
        implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.0.0'  
        kapt "androidx.room:room-compiler:$room_version"  
    }  

2. Define the entity of your model data class  
    @Entity(tableName = "base_items")
    data class BaseItem(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "subTitle") val subTitle: String
    )  
    
3. Create the DAO Interface
    @Dao  
    interface BaseItemDao{  

4. Create the Rom DB Abstract class
    @Database(entities = arrayOf(BaseItem::class), version = 1)  
    abstract class AppDatabase : RoomDatabase() {  
    
5. Instantiate the DB and perform read/writes as needed  
    val db = AppDatabase(this)  
    
    GlobalScope.launch {  
                db.baseItemDao().insertAll(*itemsList)
                data = db.baseItemDao().getAll()  
                data?.forEach {  
                    println(it)  
                }  
    }  


## Added DOKKA  
1. configure module gradle file
    dependencies {  
    ...  
    classpath "org.jetbrains.dokka:dokka-gradle-plugin:$dokka_version"
    
2. Configure app gradle file  
    apply plugin: 'org.jetbrains.dokka'  
    ...  
    dokka {  
        outputFormat = 'html' // use 'javadoc' to get standard java docs  
        outputDirectory = "$buildDir/javadoc"  
        configuration {  
            includeNonPublic = false  
            skipEmptyPackages = true  
            skipDeprecated = true  
            reportUndocumented = true  
            jdkVersion = 8  
        }  
    }  

3. Add your documentation  
4. manually trigger  
    ./gradlew dokka
