<template>
    <div>
        <b-card class="mt-0" header="전일까지 실거래를 조회할 수 있습니다.">
            <b-row class="header">
                <b-col xs="6"><a href="#" v-on:click="getList" :class="{'btn':true, 'btnActive':!isSubdivision}">아파트 실거래가</a></b-col>
                <b-col xs="6"><a href="#" v-on:click="getListBySubdivision()"  :class="{'btn':true, 'btnActive':isSubdivision}">분양권 실거래가</a></b-col>
            </b-row>
            <b-row class="pt-sm-3 mt-3">
                <b-form inline>
                    <!--label class="mr-sm-2" for="inline-form-custom-select-pref">광역시</label-->
                    <b-form-select
                            id="form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="siOptions.list"
                            v-model="siOptions.selected"
                            @change="getGuList"
                    ></b-form-select>
                </b-form>
                <b-form inline>
                    <!--label class="mr-sm-2" for="inline-form-custom-select-pref">구</label-->
                    <b-form-select
                            id="form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="guOptions.list"
                            v-model="guOptions.selected"
                            @change="getListBySelected"
                    ></b-form-select>
                </b-form>
                <b-form inline>
                    <a href="#" style="background-color:lightgoldenrodyellow; color:black; font-weight:bold; border:1px solid; padding-left:5px; padding-right:5px;">+</a>
                </b-form>
            </b-row>
            <b-row class="pt-sm-3">
                <!-- b-form inline>
                    <label class="mr-sm-2" for="inline-form-custom-select-pref">아파트 선택</label>
                    <b-form-select
                            id="form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="apartOptions.list"
                            v-model="apartOptions.selected"
                    ></b-form-select>
                </b-form -->
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
                            v-model="search"
                            type="text"
                    ></b-form-input>
                    <!--label class="mr-sm-2" for="inline-form-custom-select-pref">정렬 조건</label>
                    <b-form-select
                            id="inline-form-custom-select-pref"
                            class="mb-2 mr-sm-2 mb-sm-0"
                            :options="sortOptions.list"
                            v-model="sortOptions.selected"
                    ></b-form-select-->
                </b-form>
            </b-row>
        </b-card>
        <table  class="table">
            <thead>
                <tr>
                    <th>거래일자</th>
                    <th>아파트명</th>
                    <th>층</th>
                    <th>전용면적</th>
                    <th>실거래가</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, i) in filteredItems" :key="i">
                    <td>{{ item.거래일자 }}</td>
                    <td>{{ item.아파트명 }}</td>
                    <td>{{ item.층 }}</td>
                    <td>{{ item.전용면적 }}</td>
                    <td>{{ item.실거래가 }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "Index",
        data() {
            return {
                isSubdivision: false,
                search: '',
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
                },
                siOptions : {
                    selected: null,
                    list : []
                },
                guOptions : {
                    selected: null,
                    list : []
                },
                siList : [],
            }
        },
        mounted : function() {
            let date = new Date();
            let lastYear = date.getFullYear();
            let currentMonth = date.getMonth();
            for (var year=lastYear; year >= 2010; year--) {
                this.dealYear.list.push(year);
            }
            this.dealYear.selected = lastYear;
            this.dealMonth.selected = currentMonth;

            this.getSiList();

        },
        computed: {
            filteredItems () {
                return this.items.filter(item => {
                    return item.아파트명.toLowerCase().indexOf(this.search.toLowerCase()) > -1
                })
            }
        },
        methods : {
            getSiList : function() {
                const baseUri = '/api/sales/areacode';
                this.$http.get(baseUri+'?type=SI')
                    .then((result) => {
                        console.log(result)
                        if (result.data == undefined) { return }
                        for (var i in result.data) {
                            this.siOptions.list.push({text:result.data[i].name, value:result.data[i].code});
                            this.siList[result.data[i].code] = result.data[i].name;
                        }
                        this.siOptions.selected = result.data[0].code;
                    })
                .finally(() => {
                    this.getGuList();
                });
            },

            getGuList : function() {
                const baseUri = '/api/sales/areacode';
                const code = this.siOptions.selected;
                const siName = this.siList[this.siOptions.selected] + " ";

                this.$http.get(baseUri+'?type=GU&code=' + code)
                    .then((result) => {
                        this.guOptions.list = [];
                        if (result.data == undefined) { return }
                        for (var i in result.data) {
                            if (i == 0) continue;
                            const name = result.data[i].name.replace(siName, "");
                            this.guOptions.list.push({text:name, value:result.data[i].code});
                        }
                        this.guOptions.selected = result.data[1].code;
                        this.getListBySelected();
                    });
            },

            getListBySelected: function() {
                if (this.isSubdivision == true) {
                    this.getListBySubdivision();
                } else {
                    this.getList();
                }
            },

            getList: function() {
                const baseUri = '/api/sales';
                const dealDate = this.dealYear.selected.toString() + ("0" + this.dealMonth.selected).slice(-2);
                const code = this.guOptions.selected.substr(0, 5);

                this.isSubdivision = false;
                this.items = [];
                this.apartOptions.list = [];
                this.$http.get(baseUri+'?code='+code+'&dealYmd='+dealDate)
                    .then((result) => {
                        console.log(result.data );
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
                        //this.apartOptions.list = result.data.apartNames;
                        //this.apartOptions.selected = result.data.apartNames[0];
                    });
            },
            getListBySubdivision : function() {
                const baseUri = '/api/sales/subdivision';
                const code = this.guOptions.selected.substr(0, 5);
                const dealDate = this.dealYear.selected.toString() + ("0" + this.dealMonth.selected).slice(-2);

                this.isSubdivision = true;
                this.items = [];
                this.apartOptions.list = [];
                this.$http.get(baseUri+'?code='+code+'&dealYmd='+dealDate)
                    .then((result) => {
                        console.log(result)
                        for (var i in result.data.list) {
                            this.items.push(
                                {
                                    "거래일자" : result.data.list[i].dealDate,
                                    "아파트명" : result.data.list[i].danji,
                                    "층" : result.data.list[i].floor,
                                    "전용면적" : result.data.list[i].areaExclusiveUse,
                                    "실거래가" : result.data.list[i].soldAmount
                                }
                            )
                        }
                        //this.apartOptions.list = result.data.danji;
                        //this.apartOptions.selected = result.data.apartNames[0];
                    })
            }
        }
    }
</script>

<style scoped>
    .btn { font-size:16px; color:gray; }
    .btnActive { color:white; font-width: bold; background-color:deepskyblue; }
</style>
