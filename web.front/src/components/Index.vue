<template>
    <div>
        <b-card class="mt-0" header="아파트 실거래가 조회">
            <b-row class="header">
                <b-col xs="6"><a href="#" v-on:click="getList">아파트 매매 실거래가</a></b-col>
                <b-col xs="6"><a href="#" v-on:click="getListBySubdivision()">분양권 매매 실거래가</a></b-col>
            </b-row>un
            <b-row class="pt-sm-3">
                <b-form inline>
                    <label class="mr-sm-2" for="inline-form-custom-select-pref">아파트 선택</label>
                    <b-form-select
                            id="form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="apartOptions.list"
                            v-model="apartOptions.selected"
                    ></b-form-select>
                </b-form>
                <b-form inline class="mt-2">
                    <label class="mr-sm-2" for="inline-form-custom-select-pref">계약월(연도/월)</label>
                    <b-form-select
                            id="form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="dealYear.list"
                            v-model="dealYear.selected"
                    ></b-form-select>
                    <b-form-select
                            id="form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="dealMonth.list"
                            v-model="dealMonth.selected"
                            @change="getListBySelected"
                    ></b-form-select>
                </b-form>
                <b-form inline class="mt-2">
                    <label class="mr-sm-2" for="inline-form-custom-select-pref">아파트 입력</label>
                    <b-form-input
                            id="inline-form-input-name"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            placeholder="아파트를 입력해주세요."
                    ></b-form-input>
                    <label class="mr-sm-2" for="inline-form-custom-select-pref">정렬 조건</label>
                    <b-form-select
                            id="inline-form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="sortOptions.list"
                            v-model="sortOptions.selected"
                    ></b-form-select>
                </b-form>
            </b-row>
        </b-card>
        <b-table :items="items" :fields="fields"></b-table>
    </div>
</template>

<script>
    export default {
        name: "Index",
        data() {
            return {
                isSubdivision: false,
                fields: ["거래일자", "아파트명", "층", "전용면적", "실거래가"],
                items: [],
                sortOptions: {
                    selected: "dealDate",
                    list: [{ text: '거래일자', value: 'dealDate'},{ text: '실거래가', value: 'soldAmount'},{text: '아파트명', value: 'apartName'}]
                },
                apartOptions: {
                    selected: null,
                    list: []
                },
                dealYear: {
                    selected: null,
                    list: []
                },
                dealMonth: {
                    selected: null,
                    list: [1,2,3,4,5,6,7,8,9,10,11,12]
                }
            }
        },
        mounted : function() {
            let date = new Date();
            let lastYear = date.getFullYear();
            let currentMonth = date.getMonth() + 1;
            for (var year=lastYear; year >= 2010; year--) {
                this.dealYear.list.push(year);
            }
            this.dealYear.selected = lastYear;
            this.dealMonth.selected = currentMonth;

            this.getList();
        },
        methods : {
            getListBySelected: function() {
                if (this.isSubdivision == true) {
                    this.getListBySubdivision();
                } else {
                    this.getList();
                }
            },

            getList: function() {
                const baseUri = '/api/sales';
                const code = '28177';
                const dealDate = this.dealYear.selected.toString() + ("0" + this.dealMonth.selected).slice(-2);

                this.isSubdivision = false;
                this.items = [];
                this.apartOptions.list = [];
                this.$http.get(baseUri+'?code='+code+'&dealYmd='+dealDate)
                    .then((result) => {
                        if (result.data.list == undefined) { return }
                        for (var i in result.data.list) {
                            this.items.push(
                                {
                                    "거래일자" : result.data.list[i].dealDate,
                                    "아파트명" : result.data.list[i].apartName,
                                    "층" : result.data.list[i].floor,
                                    "전용면적" : result.data.list[i].areaExclusiveUse,
                                    "실거래가" : result.data.list[i].soldAmount
                                }
                            )
                        }
                        this.apartOptions.list = result.data.apartNames;
                        this.apartOptions.selected = result.data.apartNames[0];
                    });
            },
            getListBySubdivision : function() {
                const baseUri = '/api/sales/subdivision';
                const code = '28177';
                const dealDate = this.dealYear.selected.toString() + ("0" + this.dealMonth.selected).slice(-2);

                this.isSubdivision = true;
                this.items = [];
                this.apartOptions.list = [];
                this.$http.get(baseUri+'?code='+code+'&dealYmd='+dealDate)
                    .then((result) => {
                        for (var i in result.data.list) {
                            this.items.push(
                                {
                                    "거래일자" : result.data.list[i].dealDate,
                                    "아파트명" : result.data.list[i].apartName,
                                    "층" : result.data.list[i].floor,
                                    "전용면적" : result.data.list[i].areaExclusiveUse,
                                    "실거래가" : result.data.list[i].soldAmount
                                }
                            )
                        }
                        this.apartOptions.list = result.data.apartNames;
                        this.apartOptions.selected = result.data.apartNames[0];
                    })
            }
        }
    }
</script>

<style scoped>

</style>
