package com.murphy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.murphy.entity.Bill;
import com.murphy.entity.BillType;
import com.murphy.service.BillService;
import com.murphy.service.TypeService;
import com.murphy.vo.ResultVo;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.unbescape.json.JsonEscapeType;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 账单 - 控制器
 *
 * @author murphy
 * @since 2021/8/16 9:56 下午
 */
@RestController
@RequestMapping("/bill")
@RefreshScope
public class BillController {

    @Resource
    private BillService billService;
    @Resource
    private TypeService typeService;

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping("/list-page")
    public String listPage(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int limit,
                           Date date1, Date date2, String typeId) {
        Bill bill = new Bill();
        if (typeId != null && typeId.trim().length() != 0) {
            bill.setTypeId(new Long(typeId));
        }
        bill.setDate1(date1);
        bill.setDate2(date2);
        PageInfo<Bill> pageInfo = billService.listPage(bill, page, limit);
        List<Bill> list = pageInfo.getList();
        ResultVo vo = new ResultVo();
        vo.setData(list);
        vo.setCode(0);
        vo.setCount(pageInfo.getTotal());
        String json = JSON.toJSONString(vo);
        return json;
    }

    /**
     * 查询
     *
     * @param b
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Bill b, Model model) {
        List<BillType> types = typeService.list();
        model.addAttribute("types", types);

        List<Bill> bills = billService.list(b);
        model.addAttribute("bills", bills);

        ResultVo vo = new ResultVo();
        vo.setData(bills);
        vo.setCode(0);
        System.out.println(vo);
        String json = JSON.toJSONString(vo);
        return json;
    }

    /**
     * 账单类型
     *
     * @return
     */
    @RequestMapping("/billTypes")
    public String billTypes() {
        List<BillType> types = typeService.list();
        ResultVo vo = new ResultVo();
        vo.setData(types);
        vo.setCode(0);
        vo.setCount(Integer.toUnsignedLong(types.size()));
        String json = JSON.toJSONString(vo);
        return json;
    }

    /**
     * 添加
     *
     * @param b
     * @return
     */
    @RequestMapping("/add")
    public String add(Bill b, String callback) {
        int add = billService.add(b);
        ResultVo vo = new ResultVo();
        if (add > 0) {
            vo.setMsg("1");
            String json = JSON.toJSONString(vo);
            return callback + "(" + json + ")";
        }
        vo.setMsg("0");
        String json = JSON.toJSONString(vo);
        return callback + "(" + json + ")";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        int delete = billService.delete(id);
        ResultVo vo = new ResultVo();
        if (delete > 0) {
            vo.setMsg("1");
            String json = JSON.toJSONString(vo);
            return json;
        }
        vo.setMsg("0");
        String json = JSON.toJSONString(vo);
        return json;
    }

    /**
     * 跳转到修改
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Long id, Model model) {
        List<BillType> types = typeService.list();
        model.addAttribute("types", types);

        Bill bill = billService.get(id);
        model.addAttribute("bill", bill);

        return "/bill/update";
    }

    /**
     * 修改
     *
     * @param b
     * @return
     */
    @RequestMapping("/update")
    public String update(Bill b, String callback) {
        int update = billService.update(b);
        ResultVo vo = new ResultVo();
        if (update > 0) {
            vo.setMsg("1");
            String json = JSON.toJSONString(vo);
            return callback + "(" + json + ")";
        }
        vo.setMsg("0");
        String json = JSON.toJSONString(vo);
        return callback + "(" + json + ")";
    }
}
