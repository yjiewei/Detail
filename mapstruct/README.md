#### MapStruct
**1.不使用框架的缺点**
- 多而杂的代码与业务逻辑耦合
- 重复的劳动

**2.@Mapper**
- 默认映射规则:同类型且同名的属性会自动映射
- 自动类型转换:8种基本数据类型和string之间；日期类型和string类型之间

**3.@Mappings和@Mapping**
- 指定属性之间的映射关系
    - 日期格式化：dataFormat="yyyy-MM-dd HH:mm:ss"
    - 数字格式化
- ignore:忽略该字段的映射

**4.@AfterMapping && @MappingTarget**
- 在映射最后一步对属性的自定义映射处理

**5.@BeanMapping**
- ignoreByDefault:忽略mapStruct的默认映射行为，避免不需要的赋值，避免属性覆盖

**6.@InheritConfiguration**
- 更新的场景，避免同样的配置多写

**7.@InheritInverseConfiguration**
- 反向映射不用反过来再写一遍
