package top.belovedyaoo.openiam.core.base;

import com.mybatisflex.core.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.belovedyaoo.openiam.common.toolkit.LogUtil;
import top.belovedyaoo.openiam.core.result.Result;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static java.util.Objects.isNull;

/**
 * 基础控制器
 *
 * @author BelovedYaoo
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class BaseController<T extends BaseFiled> {

    private final BaseMapper<T> baseMapper;

    /**
     * 快速获取泛型的类型的方法
     *
     * @param objectClass 要取泛型的对应的Class
     * @param i           要取第几个泛型(0开始)
     * @param <T>         泛型类型
     *
     * @return 对应的泛型
     */
    public static <T> Class<T> getGenericClass(Class<?> objectClass, int i) {
        ParameterizedType type = (ParameterizedType) objectClass.getGenericSuperclass();
        if (type == null) {
            return null;
        }
        Type[] types = type.getActualTypeArguments();
        if (types.length > i) {
            return (Class<T>) types[i];
        }
        return null;
    }

    public Class<T> getOriginalClass() {
        return getGenericClass(this.getClass(), 0);
    }

    public <R extends BaseFiled> T convertTo(R entity) {
        Class<T> entityType = getOriginalClass();
        if (!entityType.isInstance(entity)) {
            throw new IllegalArgumentException(entity.getClass().getName() + "必须继承BaseFiled");
        }
        return entityType.cast(entity);
    }

    @GetMapping("/queryAll")
    public Result queryAll() {
        return Result.success().singleData(baseMapper.selectAll());
    }

    @PostMapping("/update")
    public Result update(@RequestBody T entity) {
        T typedEntity = convertTo(entity);
        String baseId = typedEntity.baseId();
        String message;

        if (isNull(baseMapper.selectOneById(baseId))) {
            message = "ID为" + baseId + "的数据更新失败，数据不存在";
            LogUtil.error(message);
            return Result.failed().message("更新数据失败").description(message);
        }

        boolean updateResult = baseMapper.update(typedEntity) > 0;

        if (!updateResult) {
            message = "ID为" + baseId + "的数据修改失败";
            LogUtil.error(message);
            return Result.failed().message("数据修改失败").description(message);
        }

        message = "ID为 " + baseId + " 的数据被修改";
        LogUtil.info(message);
        return Result.success().message("数据修改成功").description(message);
    }

}
