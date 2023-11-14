package com.bjoggis.linode.model.instance;

import java.util.List;

public record Page<T>(List<T> data, Integer page, Integer pages, Integer results) {

}
