package com.bjoggis.linode.model;

import com.bjoggis.linode.model.region.Resolvers;
import java.util.List;

public record Region(String id, String label, String country, List<String> capabilities, String status, Resolvers resolvers) {}

