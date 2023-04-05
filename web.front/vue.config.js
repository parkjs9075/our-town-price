module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: "http://localhost:5080",
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
};