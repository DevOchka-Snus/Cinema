package validation.model

import domain.OutputModel
import validation.model.Result

class Error (val outputModel: OutputModel) : Result()