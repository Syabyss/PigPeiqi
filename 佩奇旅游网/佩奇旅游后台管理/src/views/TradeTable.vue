<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
                >批量删除</el-button>
                <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">
                    <el-option key="1" label="广东省" value="广东省"></el-option>
                    <el-option key="2" label="湖南省" value="湖南省"></el-option>
                </el-select>
                <el-input v-model="query.name" placeholder="旅游线路" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="rid" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="rname" label="旅游线路" align="center"></el-table-column>
                <el-table-column label="缩略图" align="center" width="100">
                    <template #default="scope">
                        <el-image
                            :src="'http://localhost/travel/'+scope.row.rimage"
                            :preview-src-list="['http://localhost/travel/'+scope.row.rimage]"
                
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column label="价格" width="70" align="center">
                    <template #default="scope">¥{{ scope.row.price }}</template>
                </el-table-column>
                <el-table-column prop="routeIntroduce" label="旅游介绍" align="center"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                    @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="旅游线路">
                    <el-input v-model="form.rname" type="textarea"
                        :autosize="{ minRows: 3, maxRows: 5}"></el-input>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="form.price" ></el-input>
                </el-form-item>
                <el-form-item label="旅游介绍">
                    <el-input v-model="form.routeIntroduce" type="textarea"
                        :autosize="{ minRows: 3, maxRows: 5}"></el-input>
                </el-form-item>
                <el-form-item label="修改图片">
                    <el-image
                            v-for="url in urls" :key="url" :src="'http://localhost/travel/'+url" lazy
                            style="width: 320px; height: 200px"
                            :fit="fit"></el-image>
                </el-form-item>
                
                 
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
// import { fetchData } from "../api/index";
import { cbData  } from "../api/index";
import { dbData  } from "../api/index";
import { ebData  } from "../api/index";
export default {
    name: "tradetable",
    data() {
        return {
            
            query: {
                address: "",
                name: "",
                pageIndex: 1,
                pageSize: 10,
                rid: 1,
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
            ableData: [],
            urls: [],
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取 easy-mock 的模拟数据
        getData() {
            // fetchData(this.query).then(res => {
            //     console.log(res);
            //     this.tableData = res.list;
            //     this.pageTotal = res.pageTotal || 50;
            // });
    
            cbData(this.query).then(res => {
                console.log(res);
                this.tableData = res;
                this.pageTotal = 513||50;
            
            });
            
        },

        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, "pageIndex", 1);
            this.getData();
        },
        // 删除操作
        handleDelete(index) {
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            })
                .then(() => {
                    this.$message.success("删除成功");
                    this.tableData.splice(index, 1);
                })
                .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = "";
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + " ";
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
            this.query.rid=this.form.rid;
            dbData(this.query).then(res => {
                console.log(res);
                this.ableData = res;
                var i=0;
                
                this.urls=[];
                while(res[i].bigPic!=null){
                    this.urls[i]=res[i].bigPic;
                    i=i+1;
                }
            });
         
        },
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            console.log(this.form);
            ebData(this.form).then(res => {
                console.log(res);
            });
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, "pageIndex", val);
            this.getData();
        }
    },

};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
