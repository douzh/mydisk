package com.iteedu.stock.spider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.iteedu.base.dao.BaseManager;
import com.iteedu.stock.spider.common.utils.HttpUtil;
import com.iteedu.stock.spider.xueqiu.bean.TBalSheet;
import com.iteedu.stock.spider.xueqiu.bean.TStock;
import com.iteedu.stock.spider.xueqiu.bean.TZycwzb;
import com.iteedu.stock.spider.xueqiu.utils.XueQiuApi;

public class StockSpider {
    private static Log log=LogFactory.getLog(StockSpider.class);
    public static void main(String[] args) {
        syncBalSheet();
    }
    /**
     * 
     * @author douzh
     * @time 2015-5-6下午2:26:19
     */
    private static void syncBalSheet() {
        try {
            List<TStock> lstStock=XueQiuApi.getStockNeedBalSheet();
            if(lstStock==null||lstStock.size()==0){
                log.info("没有要同步的资产负债表");
                return;
            }
            WebClient client = HttpUtil.getClient();
            XueQiuApi.loginXueqiu(client);
            for(TStock stock:lstStock){
                try {
                List<TBalSheet> lst=XueQiuApi.getBalSheetList(client, stock.getSymbol());
                BaseManager.getDao().saveOrUpdateAll(lst);
                BaseManager.getDao().getHibernateTemplate().flush();
                log.info("资产负债表获取成功:"+stock.getSymbol());
                }catch (Exception e) {
                    log.error("资产负债表获取失败:"+stock.getSymbol());
                }
            }
        } catch (Exception e) {
            log.error("syncBalSheet出错",e);
        }
    }
    /**
     * 
     * @author douzh
     * @time 2015-5-6下午2:26:19
     */
    private static void syncZycwzb() {
        try {
            List<TStock> lstStock=XueQiuApi.getStockNeedZycwzb();
            WebClient client = HttpUtil.getClient();
            XueQiuApi.loginXueqiu(client);
            for(TStock stock:lstStock){
                List<TZycwzb> lstzb=XueQiuApi.getZycwzbList(client, stock.getSymbol());
                BaseManager.getDao().saveOrUpdateAll(lstzb);
                BaseManager.getDao().getHibernateTemplate().flush();
                log.info("主要财务指标获取成功:"+stock.getSymbol());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    
}
