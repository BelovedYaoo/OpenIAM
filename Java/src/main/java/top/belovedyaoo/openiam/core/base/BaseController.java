package top.belovedyaoo.openiam.core.base;

import com.mybatisflex.core.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.belovedyaoo.openiam.common.toolkit.LogUtil;
import top.belovedyaoo.openiam.core.log.BusinessType;
import top.belovedyaoo.openiam.core.log.InterfaceLog;
import top.belovedyaoo.openiam.core.result.Result;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * 基础控制器
 *
 * @author BelovedYaoo
 * @version 1.1
 */
@RequiredArgsConstructor
public abstract class BaseController<T extends BaseFiled> {

    /**
     * 基础Mapper
     */
    private final BaseMapper<T> baseMapper;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

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

    /**
     * 获取泛型类型对应的Class对象
     *
     * @return 具体Class对象
     */
    public Class<T> getOriginalClass() {
        return getGenericClass(this.getClass(), 0);
    }

    /**
     * 确保传入的对象 entity 是 T 的实例，并且继承自 BaseFiled<p>
     * 若满足条件，则会强制将 entity 转换为 T 类型<p>
     * 否则，抛出 IllegalArgumentException<p>
     * 除非能够保证输入完全符合预期，否则这一步检测都是必要的
     *
     * @param entity 要转换的对象
     * @param <R>    泛型类型
     *
     * @return 转换后的对象
     */
    public <R extends BaseFiled> T convertTo(R entity) {
        Class<T> entityType = getOriginalClass();
        if (!entityType.isInstance(entity)) {
            throw new IllegalArgumentException(entity.getClass().getName() + "必须继承BaseFiled");
        }
        return entityType.cast(entity);
    }

    @GetMapping("/queryAll")
    @InterfaceLog(persistence = false, print = true, businessType = BusinessType.UPDATE, identifierCode = "baseId", interfaceName = "BaseController.queryAll", interfaceDesc = "查询")
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

    @PostMapping("/delete")
    public Result delete(@RequestBody List<String> idList) {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        List<T> entityList = baseMapper.selectListByIds(idList);
        String message;

        boolean deleteResult = baseMapper.deleteBatchByIds(idList) == idList.size();

        if (!deleteResult) {
            message = "存在未能删除的数据，操作已回滚";
            LogUtil.error("数据删除失败");
            platformTransactionManager.rollback(transactionStatus);
            return Result.failed().message("数据删除失败").description(message);
        }

        message = idList.size() + "条数据被删除";
        LogUtil.info(message);

        platformTransactionManager.commit(transactionStatus);

        return Result.success().message("数据删除成功").description(message);
    }

    @PostMapping("/add")
    public Result addCourseData(@RequestBody T entity) {
        T typedEntity = convertTo(entity);
        entity.baseId(null);
        boolean addResult = baseMapper.insert(typedEntity) > 0;

        if (addResult) {
            return Result.success().message("数据新增成功");
        }

        return Result.failed().message("数据新增失败");
    }

}
