import { addClassById, globalConfig, responseToastConfig } from './globalQuote';
import { App } from 'vue';

const globalQuotePlugin = {
    install: function (app: App) {
        app.config.globalProperties.$responseToastConfig = responseToastConfig;
        app.config.globalProperties.$addClassById = addClassById;
        app.config.globalProperties.$globalConfig = globalConfig;
    }
};

export default globalQuotePlugin;
