<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 商家管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>上传信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="店家名称">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间">
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
                    <el-form-item label="选择开关">
                        <el-switch v-model="form.delivery"></el-switch>
                    </el-form-item>
                    <el-form-item label="详细地址">
                        <el-input v-model="form.address"></el-input>
                    </el-form-item>
                    <el-form-item label="经营范围">
                        <el-radio-group v-model="form.resource">
                            <el-radio label="门票"></el-radio>
                            <el-radio label="旅游攻略"></el-radio>
                            <el-radio label="旅行团"></el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="联系方式">
                        <el-input type="textarea" rows="1" v-model="form.desc"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">表单提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
import { hbData } from "../api/index";
export default {
    name: 'sellerform',
    data() {
        return {
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
                date1: '',
                desc: '',
                address:'',
            }
        };
    },
    methods: {
        onSubmit() {
            this.$message.success('提交成功！');
            hbData(this.form).then(res => {
                console.log(res);
            
            });
        }
    }
};
</script>