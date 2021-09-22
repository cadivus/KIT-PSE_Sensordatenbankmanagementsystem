const {createProxyMiddleware} = require('http-proxy-middleware')

const {env} = process

module.exports = function (app) {
  app.use(
    '/api/backend',
    createProxyMiddleware({
      target: env.API_BACKEND_URL || 'http://localhost:8081',
      changeOrigin: true,
      pathRewrite: {
        '^/api/backend': '/',
      },
    }),
  )

  app.use(
    '/api/notification',
    createProxyMiddleware({
      target: env.API_NOTIFICATION_URL || 'http://localhost:8082',
      changeOrigin: true,
      pathRewrite: {
        '^/api/notification': '/',
      },
    }),
  )
}
