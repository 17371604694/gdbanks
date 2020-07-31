package com.jeeplus.modules.bankimagemanagement.image.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.bankimagemanagement.image.entity.Image;
import com.jeeplus.modules.bankimagemanagement.image.mapper.ImageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BankImageUplaoService extends CrudService<ImageMapper,Image>{


}
