<template>
    <div>
        <b-card class="mt-0" header="아파트 실거래가 조회">
            <b-row class="header">
                <b-col md="6"><a href="#" v-on:click="getList">아파트 매매 실거래가</a></b-col>
                <b-col md="6">분양권 매매 실거래가</b-col>
            </b-row>
            <b-row>
            </b-row>
        </b-card>
    </div>
</template>

<script>
    export default {
        name: "Index",
        data: function () {
            return {
                result: []
            }
        },
        methods : {
            getList: function () {
                const key = 'Jrz2abzS5ivDmhg9LuMf5sIgK5YRHDXKJtC2GH/nkAdZgzNCYcDIulEom1Dsvm9Ry9UccXY/xDMuPnbWc5v7OQ==';
                const baseUri = '/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev';
                const code = '28177';
                const dealDate = '202010';

                this.$http.defaults.baseURL = 'http://openapi.molit.go.kr';
                this.$http.defaults.headers.get['Access-Control-Allow-Origin'] = '*';
                this.$http.defaults.headers['Access-Control-Allow-Origin'] = '*';
                this.$http.get(baseUri+'?serviceKey='+key+'&LAWD_CD='+code+'&DEAL_YMD='+dealDate+'&type=xml')
                    .then((result) => {
                        console.log(result);
                        this.result = result
                    })
            }
        }
    }
</script>

<style scoped>

</style>
