# AndroidTestingSamples

## 编写测试代码

详细的代码示例可参考Blog：[Android单元测试实践](https://yamlee.me/2023/02/20/2023-02-20-Android%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95%E5%AE%9E%E8%B7%B5/)

### Step1:配置测试环境

根据执行环境组织整理测试目录,Android Studio 中的典型项目包含两个用于放置测试的目录。请按以下方式组织整理您的测试：
* androidTest 目录应包含在真实或虚拟设备上运行的测试。此类测试包括集成测试、端到端测试，以及仅靠 JVM 无法完成应用功能验证的其他测试。
* test 目录应包含在本地计算机上运行的测试，如单元测试。

考虑在不同类型的设备上运行测试的利弊,在设备上运行测试时，您可以从以下类型中进行选择：
* 真实设备
* 虚拟设备（如 Android Studio 中的模拟器）
* 模拟设备（如 Robolectric）

真实设备可提供最高的保真度，但运行测试所花费的时间也最多。另一方面，模拟设备可提供较高的测试速度，但代价是保真度较低。不过，平台在二进制资源和逼真的循环程序上的改进使得模拟设备能够产生更逼真的结果。
虚拟设备则平衡了保真度和速度。当我们使用虚拟设备进行测试时，可以使用快照来最大限度地缩短测试之间的设置时间。

#### 添加依赖

```groovy
dependencies {
      // Core library
      androidTestImplementation 'androidx.test:core:1.0.0'


      // AndroidJUnitRunner and JUnit Rules
      androidTestImplementation 'androidx.test:runner:1.1.0'
      androidTestImplementation 'androidx.test:rules:1.1.0'


      // Assertions
      androidTestImplementation 'androidx.test.ext:junit:1.0.0'
      androidTestImplementation 'androidx.test.ext:truth:1.0.0'
      androidTestImplementation 'com.google.truth:truth:0.42'


      // Espresso dependencies
      androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.0'
      androidTestImplementation 'androidx.test.espresso:espresso-contrib:3.1.0'
      androidTestImplementation 'androidx.test.espresso:espresso-intents:3.1.0'
      androidTestImplementation 'androidx.test.espresso:espresso-accessibility:3.1.0'
      androidTestImplementation 'androidx.test.espresso:espresso-web:3.1.0'
      androidTestImplementation 'androidx.test.espresso.idling:idling-concurrent:3.1.0'


      // The following Espresso dependency can be either "implementation"
      // or "androidTestImplementation", depending on whether you want the
      // dependency to appear on your APK's compile classpath or the test APK
      // classpath.
      androidTestImplementation 'androidx.test.espresso:espresso-idling-resource:3.1.0'
    }
```

#### 整合testing模块
为了整合上面所有的测试库依赖，免除每个模块都要单独添加测试的依赖库这类繁琐重复的操作，我们在项目中可以新建一个testing模块，我们只需要添加对testing模块的依赖即可

```groovy
testImplementation project(":testing")
````

#### Gradle配置支持模拟AndroidFramework资源

本地测试依赖，需要在模块的build.gradle文件中添加如下配置

```groovy
    android {
        // ...
        testOptions {
            unitTests.includeAndroidResources = true
        }
    }
```


### Step2:创建测试


如我们在项目 **src/main/java** 目录的 **me.yamlee.testing.samples** 包名下创建了一个 **MathUtils** 的工具类，那么需要在src/test/java目录的同样包名下创建一个名为 **MathUtilsTest** 的对应测试类（可通过快捷键**ctrl+shfit+t(windows)/cmd+shift+t(mac)** 快速创建）。

#### Android平台无关测试

Android平台无关测试即不依赖Android Framework相关Api的功能代码，需要继承testing模块中的BaseUnitTest抽象类，例如下面代码所示

```kotlin
object MathUtils {

    fun add(a: Int, b: Int): Int {
        return a + b
    }
}

//测试代码
class MathUtilsTest : BaseUnitTest() {
    @Test
    fun add() {
        val result = MathUtils.add(1, 1)
        Truth.assertThat(result).isEqualTo(2)
    }
}
```

#### Android平台相关测试

对于依赖Android Framework相关Api的功能代码，如果不能使用Mockito进行手动mock，则可以使用Robolectric来进行模拟，如下代码所示，需要继承testing模块中的AndroidUnitTest抽象类

```kotlin
object StringUtils {
    fun getApplicationName(context: Context): String {
        return context.getString(R.string.app_name)
    }
}

//测试代码
class StringUtilsTest : AndroidUnitTest() {
    @Test
    fun getApplicationName() {
        val result = StringUtils.getApplicationName(applicationContext)
        Truth.assertThat(result).isEqualTo("AndroidTestingSamples")
    }
}
```

需要注意的是 AndroidUnitTest配置的单元测试runner为 **@RunWith(AndroidJUnit4.class)** ，表示JUnit的TestRunner使用AndroidJUnit的Runner，通过使用这个TestRunner，在运行测试用例时便会自动使用Android Framework Mock

```kotlin
/**
 * 需依赖Android Framework资源的测试，例如context等一些api，
 * 需要继承此类
 */
@RunWith(AndroidJUnit4::class)
abstract class AndroidUnitTest {
    protected lateinit var applicationContext: Context

    @Before
    open fun setup() {
        applicationContext = ApplicationProvider.getApplicationContext()
    }
}
```

#### Step3:运行测试

##### AndroidStudio运行

如上图所示最左边类似▶按钮点击，即可触发测试方法运行

##### 命令行运行
通过在命令行运行如下所示命令，执行app模块下的所有单元测试用例

```bash
./gradlew :app:test
```

#### Step4:查看单元测试覆盖率

测试覆盖率统计采用的是Jacoco，Android Gradle Plugin默认支持Jacoco测试覆盖率工具，通过在build.gradle配置文件中设置即可开启此功能。
项目根目录中有一个 **jacoco.gradle** 的配置文件，哪个模块需要生成测试覆盖率，可以通过如下代码引入

```groovy
apply from: '../jacoco.gradle'
```

按如上代码配置Jacoco工具后即可，通过运行对应的gradle任务，即可生成相应的测试覆盖率报告


```bash
./gradlew clean :app:jacocoTestReport
```

测试覆盖率生成完成后，可以再项目模块目录下的build目录查找生成的测试覆盖率报告。如上面代码中我们生成的是aivse模块的测试覆盖率，我们可以在 **app/build/reports/jacoco/jacocoTestReport/html/** 中通过浏览器打开 **index.html** 查看覆盖率情况

覆盖率报告大致样式如下图所示
![](https://s2.loli.net/2023/02/22/Xd5qAHJkVpRtlnK.jpg)

##### Sonar测试覆盖率关联

有的公司会通过sonarqube进行代码的静态扫描监控，我们可以把单元测试覆盖率同步到sonarqube平台上，通过一下命令可以进行测试覆盖率关联（soanrqube的扫描配置比较复杂，不在本篇展开，后续单独介绍）

```bash
# 运行单元测试，并进行各个模块的单元测试覆盖率检查
./gradlew clean jacocoTestReport

# 将各个模块的测试覆盖率汇总
./gradlew allDebugCoverage

#上传到sonarqube上
./gradlew sonarqube
```
