package net.cdsunrise.wm.system.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.system.entity.Position;
import net.cdsunrise.wm.system.entity.User;
import net.cdsunrise.wm.system.repository.DepartmentRepository;
import net.cdsunrise.wm.system.repository.PositionRepository;
import net.cdsunrise.wm.system.repository.UserRepository;
import net.cdsunrise.wm.system.service.PositionService;
import net.cdsunrise.wm.system.vo.PositionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void save(PositionVo positionVo) {
        Position position = new Position();
        if (positionVo.getId()!=null){
            position = positionRepository.findOne(positionVo.getId());
        }
        BeanUtils.copyProperties(positionVo,position);
        position.setDepartment(departmentRepository.findOne(positionVo.getDeptId()));
        positionRepository.save(position);
    }

    @Override
    public void delete(Long id) {
        List<User> userList = userRepository.findByPosition(positionRepository.findOne(id));
        for (User user:userList){
            user.setPosition(null);
        }
        userRepository.save(userList);
        positionRepository.delete(id);
    }

    @Override
    public PositionVo findOne(Long id) {
        return convertVo(positionRepository.findOne(id));
    }


    @Override
    public Pager<PositionVo> getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(Position.class, "l").useNativeSql(false).setPageCondition(condition);
        Pager<Position> pager = commonDAO.findPager(helper);
        Pager<PositionVo> p = new Pager<>(pager.getNumber(), pager.getPageSize(), pager.getTotalElements(), listConvertVO(pager.getContent()));
        return p;
    }

    @Override
    public List<PositionVo> findAll() {
        return listConvertVO(positionRepository.findAll());
    }

    @Override
    public List<PositionVo> selectPositionByDept(Long deptId) {
        return listConvertVO(positionRepository.findByDepartment(departmentRepository.findOne(deptId)));
    }

    /**
     * list  转换 listVo
     * @param positionList
     * @return
     */
    private List<PositionVo> listConvertVO(List<Position> positionList){
        List<PositionVo> positionVoList = new ArrayList<>();
        positionList.forEach(position -> positionVoList.add(convertVo(position)));
        return positionVoList;
    }

    /**
     * VO转换
     * @param position
     * @return
     */
    private PositionVo convertVo(Position position){
        if (position!=null){
            PositionVo positionVo = new PositionVo();
            BeanUtils.copyProperties(position,positionVo);
            positionVo.setDeptId(position.getDepartment().getId());
            positionVo.setDeptName(position.getDepartment().getName());
            if(position.getParentId()!=null){
                positionVo.setParentName(positionRepository.findOne(position.getParentId()).getName());
            }else{
                positionVo.setParentName("");
            }
            return positionVo;
        }else {
            return null;
        }
    }
}
