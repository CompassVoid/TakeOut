<template>
  <div class="main-content">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span>购物车</span>
        <el-button style="float:right" type="text" @click="clearCart" :disabled="items.length===0">清空</el-button>
      </div>
      <el-table :data="items" border v-loading="loading" style="width: 100%;">
        <el-table-column prop="mingcheng" label="商品" min-width="150">
          <template slot-scope="scope">
            <div class="cart-item">
              <img v-if="scope.row.tupian" :src="scope.row.tupian.split(',')[0]" class="thumb" />
              <div>
                <div class="name">{{scope.row.mingcheng}}</div>
                <div class="muted">{{scope.row.leixing}}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="shangjiamingcheng" label="商家" min-width="120"></el-table-column>
        <el-table-column prop="jiage" label="单价(元)" width="120">
          <template slot-scope="scope">
            {{Number(scope.row.jiage)||0}}
          </template>
        </el-table-column>
        <el-table-column label="数量" width="160">
          <template slot-scope="scope">
            <el-button icon="el-icon-minus" size="mini" @click="changeQty(scope.row,-1)"></el-button>
            <span class="qty">{{scope.row.goumaishuliang||1}}</span>
            <el-button icon="el-icon-plus" size="mini" @click="changeQty(scope.row,1)"></el-button>
          </template>
        </el-table-column>
        <el-table-column label="小计(元)" width="140">
          <template slot-scope="scope">
            {{(Number(scope.row.jiage)||0) * (scope.row.goumaishuliang||1)}}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" header-align="center" align="center">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-delete" @click="removeItem(scope.$index)">移除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="cart-summary">
        <div class="summary-left">
          <el-form label-width="80px">
            <el-form-item label="收货地址">
              <el-input v-model="fulladdress" placeholder="请选择地址" @click.native="openMap"></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="beizhu" type="textarea" :rows="2" placeholder="选填"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="summary-right">
          <div class="total">合计：<span class="price">{{totalPrice}}</span> 元</div>
          <el-button type="primary" :disabled="items.length===0 || !fulladdress" @click="checkout">结算下单</el-button>
        </div>
      </div>
    </el-card>

    <el-dialog width="60%" title="选择地址" :visible.sync="mapVisible" append-to-body>
      <el-amap-search-box class="search-box" :on-search-result="onSearchResult" :search-option="searchOption"></el-amap-search-box>
      <div class="amap-wrapper">
        <el-amap class="amap-box" :vid="'cart-amap'"
                 :center="center"
                 :zoom="zoom"
                 :events="events">
          <el-amap-marker v-bind:key="marker" v-for="marker in markers" :position="marker"></el-amap-marker>
        </el-amap>
      </div>
      <div>坐标：[{{ longitude }}, {{ latitude }}]，地址：{{fulladdress}}</div>
    </el-dialog>
  </div>
</template>

<script>
/* global AMap */
export default {
  data() {
    let self = this
    return {
      items: [],
      loading: false,
      beizhu: '',
      fulladdress: '',
      longitude: '',
      latitude: '',
      mapVisible: false,
      zoom: 12,
      center: [116.410426, 39.934946],
      markers: [],
      searchOption: {
        citylimit: false
      },
      events: {
        click(e) {
          let { lng, lat } = e.lnglat
          self.longitude = lng
          self.latitude = lat
          self.markers = [[lng, lat]]
          var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: 'all'
          })
          geocoder.getAddress([lng, lat], function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
              if (result && result.regeocode) {
                self.fulladdress = result.regeocode.formattedAddress
              }
            }
          })
        }
      }
    }
  },
  computed: {
    totalPrice() {
      const total = this.items.reduce((sum, item) => {
        const price = Number(item.jiage) || 0
        const qty = item.goumaishuliang || 1
        return sum + price * qty
      }, 0)
      return total.toFixed(2)
    }
  },
  mounted() {
    this.loadCart()
  },
  methods: {
    loadCart() {
      const list = this.$storage.getObj('cartItems') || []
      this.items = list.map(item => ({
        ...item,
        goumaishuliang: item.goumaishuliang || 1
      }))
    },
    saveCart() {
      this.$storage.set('cartItems', this.items)
    },
    changeQty(row, delta) {
      const qty = (row.goumaishuliang || 1) + delta
      row.goumaishuliang = qty > 1 ? qty : 1
      this.saveCart()
    },
    removeItem(index) {
      this.items.splice(index, 1)
      this.saveCart()
    },
    clearCart() {
      this.items = []
      this.saveCart()
    },
    openMap() {
      this.mapVisible = true
    },
    onSearchResult(pois) {
      let latSum = 0
      let lngSum = 0
      if (pois.length > 0) {
        pois.forEach(poi => {
          let { lng, lat } = poi
          lngSum += lng
          latSum += lat
          this.markers.push([poi.lng, poi.lat])
        })
        let center = {
          lng: lngSum / pois.length,
          lat: latSum / pois.length
        }
        this.center = [center.lng, center.lat]
      }
    },
    checkout() {
      if (this.items.length === 0) {
        this.$message.warning("购物车为空")
        return
      }
      const payload = {
        items: this.items.map(item => ({
          caipinid: item.id,
          goumaishuliang: item.goumaishuliang || 1,
          beizhu: item.beizhu
        })),
        beizhu: this.beizhu,
        fulladdress: this.fulladdress,
        longitude: this.longitude,
        latitude: this.latitude
      }
      this.loading = true
      this.$http({
        url: "dingdan/checkout",
        method: "post",
        data: payload
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message.success("下单成功")
          this.clearCart()
        } else {
          this.$message.error(data.msg || "下单失败")
        }
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.cart-item {
  display: flex;
  align-items: center;
  .thumb {
    width: 60px;
    height: 60px;
    margin-right: 12px;
    object-fit: cover;
    border-radius: 4px;
  }
  .name {
    font-weight: bold;
  }
  .muted {
    color: #888;
    font-size: 12px;
  }
}
.qty {
  display: inline-block;
  width: 40px;
  text-align: center;
}
.cart-summary {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-top: 20px;
  flex-wrap: wrap;
  .summary-left {
    flex: 1 1 60%;
    min-width: 300px;
  }
  .summary-right {
    flex: 1 1 30%;
    min-width: 200px;
    text-align: right;
    .total {
      font-size: 18px;
      margin-bottom: 10px;
      .price {
        color: #f56c6c;
        font-weight: bold;
      }
    }
  }
}
.amap-wrapper {
  width: 100%;
  height: 400px;
  margin-top: 10px;
}
.search-box {
  position: absolute;
  z-index: 10;
  width: 260px;
}
</style>
