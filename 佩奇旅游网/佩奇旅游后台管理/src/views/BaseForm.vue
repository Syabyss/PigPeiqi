<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 商品管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>商品上传</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="线路名称">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="商家选择">
                        <el-select v-model="form.region" placeholder="请选择">
                            <el-option key="bbk" label=文博小憨包 value=1></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="日期时间">
                        <el-col :span="11">
                            <el-date-picker
                                type="date"
                                placeholder="选择日期"
                                v-model="form.date1"
                                style="width: 100%;"
                            ></el-date-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="城市级联">
                        <el-cascader :options="options" v-model="form.options"></el-cascader>
                    </el-form-item>
                    <el-form-item label="价格"  >
                        <el-input v-model="form.name" size="small"></el-input>
                    </el-form-item>
                
                    <el-form-item label="旅游介绍">
                        <el-input type="textarea" rows="5" v-model="form.desc"></el-input>
                    </el-form-item>

                    <el-form-item label="主页图片">
                        <el-upload
                            class="upload-demo"
                            drag
                            action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
                            multiple>
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="详情图片">
                        <el-upload
                            class="upload-demo"
                            action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
                            :on-preview="handlePreview"
                            :on-remove="handleRemove"
                            :file-list="fileList"
                            list-type="picture">
                        <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                    </el-form-item>
                    <el-form-item>
                        <br>
                        <el-button type="primary" @click="onSubmit">表单提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>

                </el-form>
                
            </div>
        </div>
    </div>
</template>

<script>
import { abData } from "../api/index";
export default {
    name: 'baseform',
    data() {
        return {
            fileList: [],
            options: [
                {
                    value: 'liaoning',
                    label: '辽宁省',
                    children: [
                        {
                            value: 'shenyang',
                            label: '沈阳市',
                            children: [
                                {
                                    value: 'hunnan',
                                    label: '浑南区'
                                },
                                {
                                    value: 'heping',
                                    label: '和平区'
                                },
                                {
                                    value: 'shenhe',
                                    label: '沈河区'
                                },
                                {
                                    value: 'huanggu',
                                    label: '皇姑区'
                                },
                                {
                                    value: 'dadong',
                                    label: '大东区'
                                },
                                {
                                    value: 'tiexi',
                                    label: '铁西区'
                                },
                                {
                                    value: 'yuhong',
                                    label: '于洪区'
                                },
                                {
                                    value: 'shenbei',
                                    label: '沈北新区'
                                },
                                {
                                    value: 'sujiatun',
                                    label: '苏家屯区'
                                },
                                {
                                    value: 'liaozhong',
                                    label: '辽中区'
                                },
                            ]
                        },
                        {
                            value: 'dalian',
                            label: '大连市',
                            children: [
                                {
                                    value: 'lishan',
                                    label: '立山区'
                                },
                                {
                                    value: 'tiedong',
                                    label: '铁东区'
                                },
                                {
                                    value: 'tiexi',
                                    label: '铁西区'
                                },
                                {
                                    value: 'gaoxin',
                                    label: '高新区'
                                },
                            ]
                        },
                        {
                            value: 'anshan',
                            label: '鞍山市',
                            children: [
                                {
                                    value: 'lishan',
                                    label: '立山区'
                                },
                                {
                                    value: 'tiedong',
                                    label: '铁东区'
                                },
                                {
                                    value: 'tiexi',
                                    label: '铁西区'
                                },
                                {
                                    value: 'gaoxin',
                                    label: '高新区'
                                },
                            ]
                        },
                        {
                            value: 'benxi',
                            label: '本溪市',
                            children: [
                                {
                                    value: 'lishan',
                                    label: '立山区'
                                },
                                {
                                    value: 'tiedong',
                                    label: '铁东区'
                                },
                                {
                                    value: 'tiexi',
                                    label: '铁西区'
                                },
                                {
                                    value: 'gaoxin',
                                    label: '高新区'
                                },
                            ]
                        }
                    ]
                },
                {
                    value: 'beijing',
                    label: '北京市',
                },
                {
                    value: 'hebei',
                    label: '河北省',
                },
                {
                    value: 'tianjin',
                    label: '天津市',
                },
            ],
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: true,
                type: ['步步高'],
                resource: '小天才',
                desc: '',
                options: []
            }
        };
    },
    methods: {
        getData() {
            // fetchData(this.query).then(res => {
            //     console.log(res);
            //     this.tableData = res.list;
            //     this.pageTotal = res.pageTotal || 50;
            // });
            abData(this.query).then(res => {
                console.log(res);
                this.tableData = res;
                this.pageTotal = 1;
                this.name=res.name;
            });
        },
        onSubmit() {
            this.$message.success('提交成功！');
        },

        handleRemove(file, fileList) {
        console.log(file, fileList);
        },

        handlePreview(file) {
            console.log(file);
        }
    }
};
</script>