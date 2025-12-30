package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.DingdanEntity;
import com.entity.PeisongdanEntity;
import com.entity.view.PeisongdanView;

import com.service.DingdanService;
import com.service.PeisongdanService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 配送单
 * 后端接口
 * @author 
 * @email 
 * @date 2020-09-23 18:00:25
 */
@RestController
@RequestMapping("/peisongdan")
public class PeisongdanController {
    @Autowired
    private PeisongdanService peisongdanService;
    @Autowired
    private DingdanService dingdanService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,PeisongdanEntity peisongdan, HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shangjia")) {
			peisongdan.setShangjiabianhao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("yonghu")) {
			peisongdan.setZhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("qishou")) {
			peisongdan.setYonghuming((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<PeisongdanEntity> ew = new EntityWrapper<PeisongdanEntity>();
		PageUtils page = peisongdanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, peisongdan), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,PeisongdanEntity peisongdan, HttpServletRequest request){
        EntityWrapper<PeisongdanEntity> ew = new EntityWrapper<PeisongdanEntity>();
		PageUtils page = peisongdanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, peisongdan), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( PeisongdanEntity peisongdan){
       	EntityWrapper<PeisongdanEntity> ew = new EntityWrapper<PeisongdanEntity>();
      	ew.allEq(MPUtil.allEQMapPre( peisongdan, "peisongdan")); 
        return R.ok().put("data", peisongdanService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(PeisongdanEntity peisongdan){
        EntityWrapper< PeisongdanEntity> ew = new EntityWrapper< PeisongdanEntity>();
 		ew.allEq(MPUtil.allEQMapPre( peisongdan, "peisongdan")); 
		PeisongdanView peisongdanView =  peisongdanService.selectView(ew);
		return R.ok("查询配送单成功").put("data", peisongdanView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        PeisongdanEntity peisongdan = peisongdanService.selectById(id);
        return R.ok().put("data", peisongdan);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") String id){
        PeisongdanEntity peisongdan = peisongdanService.selectById(id);
        return R.ok().put("data", peisongdan);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PeisongdanEntity peisongdan, HttpServletRequest request){
    	peisongdan.setId(generateId());
        if(peisongdan.getDingdanid() != null){
            DingdanEntity order = dingdanService.selectById(peisongdan.getDingdanid());
            if(order != null && !"?????????".equals(order.getSfsh())){
                return R.error("??????????????????");
            }
        }
        prepareFromOrder(peisongdan, request);
        peisongdanService.insert(peisongdan);
        markOrderAccepted(peisongdan.getDingdanid());
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody PeisongdanEntity peisongdan, HttpServletRequest request){
    	peisongdan.setId(generateId());
        if(peisongdan.getDingdanid() != null){
            DingdanEntity order = dingdanService.selectById(peisongdan.getDingdanid());
            if(order != null && !"?????????".equals(order.getSfsh())){
                return R.error("??????????????????");
            }
        }
        prepareFromOrder(peisongdan, request);
        peisongdanService.insert(peisongdan);
        markOrderAccepted(peisongdan.getDingdanid());
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PeisongdanEntity peisongdan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(peisongdan);
        peisongdanService.updateById(peisongdan);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        peisongdanService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<PeisongdanEntity> wrapper = new EntityWrapper<PeisongdanEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("shangjia")) {
			wrapper.eq("shangjiabianhao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("yonghu")) {
			wrapper.eq("zhanghao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("qishou")) {
			wrapper.eq("yonghuming", (String)request.getSession().getAttribute("username"));
		}

		int count = peisongdanService.selectCount(wrapper);
		return R.ok().put("count", count);
	}

    private Long generateId(){
        return new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue();
    }

    private void prepareFromOrder(PeisongdanEntity peisongdan, HttpServletRequest request){
        if(peisongdan.getJiedanshijian() == null){
            peisongdan.setJiedanshijian(new Date());
        }
        if(StringUtils.isBlank(peisongdan.getDingdanzhuangtai())){
            peisongdan.setDingdanzhuangtai("已接单");
        }
        Object usernameObj = request.getSession().getAttribute("username");
        Object tableNameObj = request.getSession().getAttribute("tableName");
        if(usernameObj != null && tableNameObj != null && "qishou".equals(tableNameObj.toString())){
            peisongdan.setYonghuming(usernameObj.toString());
        }
        if(peisongdan.getDingdanid() != null){
            DingdanEntity order = dingdanService.selectById(peisongdan.getDingdanid());
            if(order != null){
                peisongdan.setQishoushouyi(order.getQishoushouyi());
                peisongdan.setMingcheng(order.getMingcheng());
                peisongdan.setTupian(order.getTupian());
                peisongdan.setGoumaishuliang(order.getGoumaishuliang() == null ? null : order.getGoumaishuliang().toString());
                peisongdan.setShangjiabianhao(order.getShangjiabianhao());
                peisongdan.setZhanghao(order.getZhanghao());
                if(StringUtils.isBlank(peisongdan.getBeizhu())){
                    peisongdan.setBeizhu(order.getBeizhu());
                }
                peisongdan.setLongitude(order.getLongitude());
                peisongdan.setLatitude(order.getLatitude());
                peisongdan.setFulladdress(order.getFulladdress());
            }
        }
    }

    private void markOrderAccepted(Long dingdanId){
        if(dingdanId != null){
            DingdanEntity order = dingdanService.selectById(dingdanId);
            if(order != null){
                order.setSfsh("已接单");
                dingdanService.updateById(order);
            }
        }
    }
}
