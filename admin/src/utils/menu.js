const menu = {
    list() {
        return [
            {
                backMenu: [
                    {
                        menu: "用户管理",
                        child: [
                            { menu: "用户列表", menuJump: "列表", tableName: "yonghu", buttons: ["新增", "查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "商家管理",
                        child: [
                            { menu: "商家列表", menuJump: "列表", tableName: "shangjia", buttons: ["新增", "查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "菜品分类管理",
                        child: [
                            { menu: "菜品分类列表", menuJump: "列表", tableName: "caipinfenlei", buttons: ["新增", "查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "骑手管理",
                        child: [
                            { menu: "骑手列表", menuJump: "列表", tableName: "qishou", buttons: ["新增", "查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "系统管理",
                        child: [
                            { menu: "轮播图管理", tableName: "lunbotuguanli", buttons: ["新增", "查看", "修改", "删除"] },
                            { menu: "新闻资讯列表", tableName: "news", buttons: ["新增", "查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "菜品管理",
                        child: [
                            { menu: "菜品列表", menuJump: "列表", tableName: "caipin", buttons: ["查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "订单管理",
                        child: [
                            { menu: "订单列表", menuJump: "列表", tableName: "dingdan", buttons: ["查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "配送单管理",
                        child: [
                            { menu: "配送单列表", menuJump: "列表", tableName: "peisongdan", buttons: ["查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "商品评价管理",
                        child: [
                            { menu: "商品评价列表", menuJump: "列表", tableName: "shangpinpingjia", buttons: ["查看", "修改", "删除"] }
                        ]
                    }
                ],
                frontMenu: [
                    {
                        menu: "商家模块",
                        child: [{ menu: "商家列表", menuJump: "列表", tableName: "shangjia", buttons: ["查看"] }]
                    },
                    {
                        menu: "菜品模块",
                        child: [{ menu: "菜品列表", menuJump: "列表", tableName: "caipin", buttons: ["查看", "加入订餐车"] }]
                    }
                ],
                roleName: "管理员",
                tableName: "users"
            },
            {
                backMenu: [
                    {
                        menu: "订单管理",
                        child: [
                            { menu: "订单列表", menuJump: "列表", tableName: "dingdan", buttons: ["查看", "修改", "删除", "支付"] }
                        ]
                    },
                    {
                        menu: "购物车",
                        child: [
                            { menu: "购物车", menuJump: "列表", tableName: "cart", buttons: ["查看", "删除", "提交"] }
                        ]
                    },
                    {
                        menu: "配送单管理",
                        child: [
                            { menu: "配送单列表", menuJump: "列表", tableName: "peisongdan", buttons: ["查看", "审核", "评价"] }
                        ]
                    },
                    {
                        menu: "商品评价管理",
                        child: [
                            { menu: "商品评价列表", menuJump: "列表", tableName: "shangpinpingjia", buttons: ["查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "我的收藏管理",
                        child: [
                            { menu: "收藏列表", tableName: "storeup", buttons: ["查看", "删除"] }
                        ]
                    }
                ],
                frontMenu: [
                    {
                        menu: "商家模块",
                        child: [{ menu: "商家列表", menuJump: "列表", tableName: "shangjia", buttons: ["查看"] }]
                    },
                    {
                        menu: "菜品模块",
                        child: [{ menu: "菜品列表", menuJump: "列表", tableName: "caipin", buttons: ["查看", "加入订餐车"] }]
                    }
                ],
                roleName: "用户",
                tableName: "yonghu"
            },
            {
                backMenu: [
                    {
                        menu: "菜品管理",
                        child: [
                            { menu: "菜品列表", menuJump: "列表", tableName: "caipin", buttons: ["新增", "查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "订单管理",
                        child: [
                            { menu: "订单列表", menuJump: "列表", tableName: "dingdan", buttons: ["查看", "审核"] }
                        ]
                    },
                    {
                        menu: "配送单管理",
                        child: [
                            { menu: "配送单列表", menuJump: "列表", tableName: "peisongdan", buttons: ["查看", "支付"] }
                        ]
                    },
                    {
                        menu: "商品评价管理",
                        child: [
                            { menu: "商品评价列表", menuJump: "列表", tableName: "shangpinpingjia", buttons: ["查看", "审核"] }
                        ]
                    },
                    {
                        menu: "我的收藏管理",
                        child: [
                            { menu: "收藏列表", tableName: "storeup", buttons: ["查看", "删除"] }
                        ]
                    }
                ],
                frontMenu: [
                    {
                        menu: "商家模块",
                        child: [{ menu: "商家列表", menuJump: "列表", tableName: "shangjia", buttons: ["查看"] }]
                    },
                    {
                        menu: "菜品模块",
                        child: [{ menu: "菜品列表", menuJump: "列表", tableName: "caipin", buttons: ["查看", "加入订餐车"] }]
                    }
                ],
                roleName: "商家",
                tableName: "shangjia"
            },
            {
                backMenu: [
                    {
                        menu: "订单管理",
                        child: [
                            { menu: "订单列表", menuJump: "列表", tableName: "dingdan", buttons: ["查看", "配送"] }
                        ]
                    },
                    {
                        menu: "配送单管理",
                        child: [
                            { menu: "配送单列表", menuJump: "列表", tableName: "peisongdan", buttons: ["查看", "修改", "删除"] }
                        ]
                    },
                    {
                        menu: "商品评价管理",
                        child: [
                            { menu: "商品评价列表", menuJump: "列表", tableName: "shangpinpingjia", buttons: ["查看"] }
                        ]
                    }
                ],
                frontMenu: [
                    {
                        menu: "商家模块",
                        child: [{ menu: "商家列表", menuJump: "列表", tableName: "shangjia", buttons: ["查看"] }]
                    },
                    {
                        menu: "菜品模块",
                        child: [{ menu: "菜品列表", menuJump: "列表", tableName: "caipin", buttons: ["查看", "加入订餐车"] }]
                    }
                ],
                roleName: "骑手",
                tableName: "qishou"
            }
        ];
    }
};
export default menu;
