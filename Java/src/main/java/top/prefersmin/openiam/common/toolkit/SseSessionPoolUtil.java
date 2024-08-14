package top.prefersmin.openiam.common.toolkit;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SSE会话池工具类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Component
public class SseSessionPoolUtil {

    /**
     * SSE会话池
     */
    public static final Map<String, SseEmitter> SSE_SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 默认超时时间(当前为:永不超时)
     */
    private static final long DEFAULT_TIMEOUT = 0L;

    /**
     * 判断SSE会话是否存在
     *
     * @param sessionId SSE会话UUID
     *
     * @return 会话存在状态
     */
    public static boolean isSessionExist(String sessionId) {
        return SSE_SESSION_POOL.get(sessionId) != null;
    }

    /**
     * 创建指定UUID与SSE对象的会话
     *
     * @param sessionId SSE会话UUID
     * @param sseEmitter  SSE对象
     */
    public static void createSession(String sessionId, SseEmitter sseEmitter) {
        SSE_SESSION_POOL.put(sessionId, sseEmitter);
    }

    /**
     * 创建指定UUID与超时时间的会话
     *
     * @param sessionId SSE会话UUID
     * @param timeOut     超时时间
     */
    public static void createSession(String sessionId, long timeOut) {
        createSession(sessionId, new SseEmitter(timeOut));
    }

    /**
     * 创建指定UUID与默认超时时间的会话
     *
     * @param sessionId SSE会话UUID
     */
    public static SseEmitter createSession(String sessionId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        createSession(sessionId, sseEmitter);
        return sseEmitter;
    }

    /**
     * 创建指定SSE对象的会话，并返回SSE会话UUID
     *
     * @param sseEmitter SSE对象
     *
     * @return SSE会话UUID
     */
    public static String createSession(SseEmitter sseEmitter) {
        String simpleUuid = IdUtil.simpleUUID();
        createSession(simpleUuid, sseEmitter);
        return simpleUuid;
    }

    /**
     * 创建指定超时时间的会话，并返回SSE会话UUID
     *
     * @param timeOut 超时时间
     *
     * @return SSE会话UUID
     */
    public static String createSession(long timeOut) {
        String simpleUuid = IdUtil.simpleUUID();
        createSession(simpleUuid, new SseEmitter(timeOut));
        return simpleUuid;
    }

    /**
     * 创建默认超时时间的会话，并返回SSE会话UUID
     *
     * @return SSE会话UUID
     */
    public static String createSession() {
        return createSession(DEFAULT_TIMEOUT);
    }

    /**
     * 获取指定SSE会话UUID的SSE对象
     *
     * @param sessionId SSE会话UUID
     *
     * @return SSE对象
     */
    public static SseEmitter getSession(String sessionId) {
        if (isSessionExist(sessionId)) {
            return SSE_SESSION_POOL.get(sessionId);
        }
        throw new RuntimeException("SSE会话不存在");
    }

    /**
     * 获取或创建SSE会话UUID的SSE对象
     *
     * @param sessionId SSE会话UUID
     */
    public static SseEmitter getOrCreateSession(String sessionId) {
        if (isSessionExist(sessionId)) {
            return getSession(sessionId);
        }
        return createSession(sessionId);
    }

    /**
     * 移除指定SSE会话UUID的SSE对象
     *
     * @param sessionId SSE会话UUID
     */
    public static void removeSession(String sessionId) {
        if (isSessionExist(sessionId)) {
            SSE_SESSION_POOL.remove(sessionId);
        }
    }

}
