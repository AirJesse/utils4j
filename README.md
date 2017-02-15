##java自用开发工具包
封装了部分常用工具，主要用来在编写java代码时偷懒。
> 偷懒，是成为优秀程序员的最佳驱动力之一。
>                          ——沃·兹基硕德

##部分常用方法简单介绍
### LjcReflectionUtils类： 反射相关工具
* copy：将一个对象(包括其父类)中的同名同类型属性复制到另一个对象中。包含所有父类属性，无视两者对象类型，无视修饰符范围。

### LjcArrayUtils类： 数组相关工具
* append: 添加单个元素或另一个数组的所有元素进目标数组。
* remove: 从数组中移除元素。
* subArray:切割数组。
* contains:检查数组中是否包含目标元素。
* toArray:用于包装类数组与基本类型数组互相转换。

### LjcStringUtils类： 字符串相关工具
* isEmpty: 判断字符串是否为空或null。
* containsChinese: 判断字符串中是否包含中文。
* getHexStringFromByteArray: 转换为十六进制字符串
* getDateFromString: string转date（无异常）

** 全部方法可自行查看源代码 **

** 更多工具等待本人整理后发布 **

## 使用方法:

将*release*中的 [ljc-utils-1.0.jar]("https://github
.com/AirJesse/AirJesseDevelopmentSupport/blob/master/release/ljc-utils-1.0.jar")添加进自己的项目中。
